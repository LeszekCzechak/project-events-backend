package pl.sdacademy.projecteventsbackend.User;

import org.springframework.stereotype.Service;
import pl.sdacademy.projecteventsbackend.User.dto.RegisterUserRequest;
import pl.sdacademy.projecteventsbackend.User.dto.UserResponse;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse registerUser(RegisterUserRequest newUser) {
        UserEntity userEntity=new UserEntity();
        userEntity.setUsername(newUser.getName());
        userEntity.setPassword(newUser.getPassword());
        userEntity.setMail(newUser.getMail());

        userRepository.save(userEntity);

        UserResponse response= new UserResponse(userEntity.getUsername(), userEntity.getMail());
        return response;
    }

    public UserResponse getUserById(long userId) {
        UserEntity userEntity = userRepository.getOne(userId);
        return new UserResponse(userEntity.getUsername(), userEntity.getMail());
    }
}