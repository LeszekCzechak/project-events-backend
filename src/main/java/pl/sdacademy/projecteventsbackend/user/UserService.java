package pl.sdacademy.projecteventsbackend.user;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sdacademy.projecteventsbackend.component.mailService.MailService;
import pl.sdacademy.projecteventsbackend.component.userContext.UserContext;
import pl.sdacademy.projecteventsbackend.component.util.JwtUtil;
import pl.sdacademy.projecteventsbackend.user.dto.*;
import pl.sdacademy.projecteventsbackend.user.model.UserEntity;
import pl.sdacademy.projecteventsbackend.user.model.UserRole;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserContext userContext;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                       UserContext userContext, MailService mailService,
                       @Lazy AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userContext = userContext;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
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

        if (!currentUser.getUsername().equals(userEntity.getUsername()) || !currentUser.getEnabled()) {
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
        userEntity.setEnabled(true);

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

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    (new UsernamePasswordAuthenticationToken
                            (authenticationRequest.getUsername(), authenticationRequest.getPassword())));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect Username or Password", e);
        }

        UserDetails userDetails = loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        String username = userDetails.getUsername();
        AuthenticationResponse response = new AuthenticationResponse(jwt, username);
        return response;
    }
}
