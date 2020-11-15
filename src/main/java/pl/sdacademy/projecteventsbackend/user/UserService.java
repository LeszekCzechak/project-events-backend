package pl.sdacademy.projecteventsbackend.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sdacademy.projecteventsbackend.user.dto.EditUserRequest;
import pl.sdacademy.projecteventsbackend.user.dto.RegisterUserRequest;
import pl.sdacademy.projecteventsbackend.user.dto.UserResponse;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse registerUser(RegisterUserRequest newUser) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(newUser.getName());
        userEntity.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userEntity.setMail(newUser.getMail());
        userEntity.setAccountNonExpired(true);
        userEntity.setAccountNonLocked(true);
        userEntity.setCreatedUserDate(LocalDateTime.now());
        userEntity.setCredentialsNonExpired(true);
        userEntity.setEnabled(true);
        userEntity.setRoles(Collections.singleton(UserRole.USER));
        userEntity.setUpdatedOn(LocalDateTime.now());

        userRepository.save(userEntity);

        UserResponse response = new UserResponse(userEntity.getUsername(), userEntity.getMail());
        return response;
    }

    public UserResponse getUserById(long userId) {
        UserEntity userEntity = userRepository.getOne(userId);
        return new UserResponse(userEntity.getUsername(), userEntity.getMail());
    }

    public UserResponse updateUserByUserId(long userId, EditUserRequest editedData) {
        UserEntity userEntity = userRepository.getOne(userId);
        userEntity.setUsername(editedData.getName());
        userEntity.setMail(editedData.getMail());
        userRepository.save(userEntity);

        UserResponse response = new UserResponse(userEntity.getUsername(), userEntity.getMail());
        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
            return userRepository.getUserEntityByUsername(s)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}