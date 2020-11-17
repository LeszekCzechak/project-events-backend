package pl.sdacademy.projecteventsbackend.component.userContext;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.sdacademy.projecteventsbackend.User.UserEntity;
import pl.sdacademy.projecteventsbackend.User.UserRepository;

public class UserContextImpl implements UserContext{

    private UserEntity userEntity;
    private final UserRepository userRepository;

    public UserContextImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void setUserEntity(UserEntity userEntity) {
        this.userEntity= userEntity;
    }

    @Override
    public UserEntity getCurrentUser() {
        return userRepository.findById(1L).orElseThrow(()-> new UsernameNotFoundException("Can't find User id:1"));
    }
}
