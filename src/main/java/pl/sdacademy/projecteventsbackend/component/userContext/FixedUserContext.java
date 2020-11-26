package pl.sdacademy.projecteventsbackend.component.userContext;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.sdacademy.projecteventsbackend.user.model.UserEntity;
import pl.sdacademy.projecteventsbackend.user.UserRepository;

@Component
public class FixedUserContext implements UserContext {
    private final UserRepository userRepository;

    public FixedUserContext(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity getCurrentUser() {
        return userRepository.findById(1L).orElseThrow(() -> new UsernameNotFoundException("Can't find User id:1"));
    }
}
