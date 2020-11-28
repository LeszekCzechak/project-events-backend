package pl.sdacademy.projecteventsbackend.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.sdacademy.projecteventsbackend.component.util.JwtUtil;
import pl.sdacademy.projecteventsbackend.user.dto.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUser(@RequestBody RegisterUserRequest newUser) {
        UserResponse createdUser = userService.sendRegistrationEmail(newUser);
        return createdUser;
    }

    @GetMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoggedUser authenticate() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        LoggedUser loggedUser = new LoggedUser(username);
        return loggedUser;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse editUser(@PathVariable("id") long userId, @RequestBody EditUserRequest editedData) {
        return userService.updateUserByUserId(userId, editedData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long userId) {
        HttpStatus httpStatus = userService.deleteUserById(userId);
        return new ResponseEntity<>(httpStatus);
    }

    @GetMapping("/register/{username}/{uuid}")
    public void activateAccount(@PathVariable("username") String username, @PathVariable("uuid") String uuid) {
        userService.activateAccount(username, uuid);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    (new UsernamePasswordAuthenticationToken
                            (authenticationRequest.getUsername(), authenticationRequest.getPassword())));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect Username or Password", e);
        }

        UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        String username = userDetails.getUsername();
        return ResponseEntity.ok(new AuthenticationResponse(jwt, username));
    }
}
