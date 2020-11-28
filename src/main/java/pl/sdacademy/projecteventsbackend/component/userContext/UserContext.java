package pl.sdacademy.projecteventsbackend.component.userContext;

import pl.sdacademy.projecteventsbackend.user.model.UserEntity;

public interface UserContext {
    UserEntity getCurrentUser();
}
