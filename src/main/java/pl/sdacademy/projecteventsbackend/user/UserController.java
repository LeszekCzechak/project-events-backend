package pl.sdacademy.projecteventsbackend.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.sdacademy.projecteventsbackend.user.dto.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
}
