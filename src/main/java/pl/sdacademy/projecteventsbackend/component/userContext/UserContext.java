package pl.sdacademy.projecteventsbackend.component.userContext;

import pl.sdacademy.projecteventsbackend.user.model.UserEntity;

public interface UserContext {

    public void setUserEntity(UserEntity userEntity);

    public UserEntity getCurrentUser();
}
