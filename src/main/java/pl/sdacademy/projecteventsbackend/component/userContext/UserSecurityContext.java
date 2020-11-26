package pl.sdacademy.projecteventsbackend.component.userContext;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.sdacademy.projecteventsbackend.user.model.UserEntity;

@Component
@Primary
public class UserSecurityContext implements UserContext {

    @Override
    public UserEntity getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        return (UserEntity) context.getAuthentication().getPrincipal();
    }
}
