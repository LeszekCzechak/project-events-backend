package pl.sdacademy.projecteventsbackend.component.userContext;

import org.springframework.stereotype.Component;
import pl.sdacademy.projecteventsbackend.user.model.UserEntity;

@Component
public interface UserContext {

    public void setUserEntity(UserEntity userEntity);

    public UserEntity getCurrentUser();
}
