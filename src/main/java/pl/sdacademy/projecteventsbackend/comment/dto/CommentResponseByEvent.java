package pl.sdacademy.projecteventsbackend.comment.dto;

import pl.sdacademy.projecteventsbackend.user.dto.LoggedUser;
import pl.sdacademy.projecteventsbackend.user.model.UserEntity;

public class CommentResponseByEvent {
    private String username;
    private String content;

    public CommentResponseByEvent(String username, String content) {
        this.username = username;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
