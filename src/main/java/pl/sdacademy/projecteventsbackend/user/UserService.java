package pl.sdacademy.projecteventsbackend.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sdacademy.projecteventsbackend.component.mailService.MailService;
import pl.sdacademy.projecteventsbackend.component.userContext.UserContext;
import pl.sdacademy.projecteventsbackend.user.dto.EditUserRequest;
import pl.sdacademy.projecteventsbackend.user.dto.RegisterUserRequest;
import pl.sdacademy.projecteventsbackend.user.dto.UserResponse;
import pl.sdacademy.projecteventsbackend.user.model.UserEntity;
import pl.sdacademy.projecteventsbackend.user.model.UserRole;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class UserService implements UserDetailsService, ConnectionSignUp {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserContext userContext;
    private final MailService mailService;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, UserContext userContext, MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userContext = userContext;
        this.mailService = mailService;
    }

    public UserResponse registerUser(RegisterUserRequest newUser) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(newUser.getName());
        userEntity.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userEntity.setUuidUser(uuidGenerator(newUser));
        userEntity.setMail(newUser.getMail());
        userEntity.setAccountNonExpired(true);
        userEntity.setAccountNonLocked(true);
        userEntity.setCreatedUserDate(LocalDateTime.now());
        userEntity.setCredentialsNonExpired(true);
        userEntity.setEnabled(true);
        userEntity.setRoles(Collections.singleton(UserRole.USER));
        userEntity.setUpdatedOn(LocalDateTime.now());
        userEntity.setDateOfBirth(newUser.getDateOfBirth());

        userRepository.save(userEntity);
        try {
            mailService.sendMail(newUser.getMail(),
                    "Hi, it's me","You just register on the best app ever!",false);
        } catch (MessagingException e) {

        }

        UserResponse response = new UserResponse(userEntity.getId(), userEntity.getUsername(), userEntity.getMail(), userEntity.getDateOfBirth());
        return response;
    }

    private String uuidGenerator(RegisterUserRequest newUser) {
        String textToUuid= ""+ newUser.getName()+ newUser.getPassword();
        String uuidUser = UUID.nameUUIDFromBytes(textToUuid.getBytes()).toString();
        return uuidUser;
    }

    public UserResponse getUserById(long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserResponse(userEntity.getId(), userEntity.getUsername(), userEntity.getMail(), userEntity.getDateOfBirth());
    }

    @Transactional
    public UserResponse updateUserByUserId(long userId, EditUserRequest editedData) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserEntity currentUser = userContext.getCurrentUser();

        if (!currentUser.equals(userEntity) || !currentUser.getEnabled()) {
            throw new AccessDeniedException("Can't do that");
        }
        userEntity.setUsername(editedData.getName());
        userEntity.setMail(editedData.getMail());
        userEntity.setDateOfBirth(editedData.getDateOfBirth());
        userEntity.setUpdatedOn(LocalDateTime.now());
        userRepository.save(userEntity);
        UserResponse response = new UserResponse(userEntity.getId(), userEntity.getUsername(), userEntity.getMail(), userEntity.getDateOfBirth());
        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findUserEntityByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public HttpStatus deleteUserById(long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userEntity.setEnabled(false);
        userEntity.setAccountNonExpired(false);
        userEntity.setAccountNonLocked(false);
        userEntity.setCredentialsNonExpired(false);
        userEntity.setUpdatedOn(LocalDateTime.now());
        userRepository.save(userEntity);
        return HttpStatus.OK;
    }

    @Override
    public String execute(Connection<?> connection) {
        UserEntity userEntity= new UserEntity();
        userEntity.setUsername(connection.getDisplayName());
        userEntity.setPassword(passwordEncoder.encode("user"));
        userEntity.setMail(null);
        userEntity.setAccountNonExpired(true);
        userEntity.setAccountNonLocked(true);
        userEntity.setCreatedUserDate(LocalDateTime.now());
        userEntity.setCredentialsNonExpired(true);
        userEntity.setEnabled(true);
        userEntity.setRoles(Collections.singleton(UserRole.USER));
        userEntity.setUpdatedOn(LocalDateTime.now());
        userEntity.setDateOfBirth(LocalDate.now());
        userRepository.save(userEntity);
        return userEntity.getUsername();
    }

    public UserResponse sendRegistrationEmail(RegisterUserRequest newUser){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(newUser.getName());
        userEntity.setPassword(passwordEncoder.encode(newUser.getPassword()));
        String uuidUser = uuidGenerator(newUser);
        userEntity.setUuidUser(uuidUser);
        userEntity.setMail(newUser.getMail());
        userEntity.setAccountNonExpired(true);
        userEntity.setAccountNonLocked(true);
        userEntity.setCreatedUserDate(LocalDateTime.now());
        userEntity.setCredentialsNonExpired(true);
        userEntity.setRoles(Collections.singleton(UserRole.USER));
        userEntity.setUpdatedOn(LocalDateTime.now());
        userEntity.setDateOfBirth(newUser.getDateOfBirth());
        userEntity.setEnabled(false);

        userRepository.save(userEntity);
        try {
            String activationLink= "http://localhost:8080/user/register/"+userEntity.getUsername()+"/"+uuidUser;

            String url = "<a href=\"" + activationLink + "\">Aktywuj</a>";

            mailService.sendMail(newUser.getMail(),
                    "Registration link",
                    "Hi " + newUser.getName() + ",\n" +
                            "Click link to activate Your account.\n"
                            + url,
                    true);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println(e);
        }

        UserResponse response = new UserResponse(userEntity.getId(), userEntity.getUsername(),
                userEntity.getMail(), userEntity.getDateOfBirth());
        return response;
    }

    @Transactional
    public void activateAccount(String username, String uuid) {
        UserEntity userEntity = userRepository.findUserEntityByUuidUser(uuid)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(username.equals(userEntity.getUsername())){
            userEntity.setEnabled(true);
            userRepository.save(userEntity);
        } else throw new AccessDeniedException("You can't do that");
    }
}
