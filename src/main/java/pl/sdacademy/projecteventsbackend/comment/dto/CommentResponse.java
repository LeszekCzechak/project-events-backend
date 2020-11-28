package pl.sdacademy.projecteventsbackend.comment.dto;

import pl.sdacademy.projecteventsbackend.user.model.UserEntity;

import java.time.LocalDateTime;

public class CommentResponse {

    private Long id;
    private UserEntity userName;
    private String content;
    private LocalDateTime createdOn;

    public CommentResponse(Long id, UserEntity userName, String content, LocalDateTime createdOn) {
        this.id = id;
        this.userName = userName;
        this.content = content;
        this.createdOn = createdOn;
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
