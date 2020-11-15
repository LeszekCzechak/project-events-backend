package pl.sdacademy.projecteventsbackend.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sdacademy.projecteventsbackend.User.dto.RegisterUserRequest;
import pl.sdacademy.projecteventsbackend.User.dto.UserResponse;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController( UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUser(@RequestBody RegisterUserRequest newUser){
        UserResponse createdUser = userService.registerUser(newUser);
        return createdUser;
    }
}
