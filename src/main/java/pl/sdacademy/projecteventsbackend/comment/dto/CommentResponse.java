package pl.sdacademy.projecteventsbackend.comment.dto;

import pl.sdacademy.projecteventsbackend.user.model.UserEntity;

public class CommentResponse {

    private Long id;
    private UserEntity userName;
    private String content;

    public CommentResponse(Long id, UserEntity userName, String content) {
        this.id = id;
        this.userName = userName;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUserName() {
        return userName;
    }

    public void setUserName(UserEntity userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
