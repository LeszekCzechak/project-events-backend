package pl.sdacademy.projecteventsbackend.comment.dto;

import pl.sdacademy.projecteventsbackend.event.EventEntity;
import pl.sdacademy.projecteventsbackend.user.model.UserEntity;

import javax.validation.constraints.NotBlank;

public class EditCommentRequest {

    @NotBlank(message = "This field must not be empty")
    private UserEntity name;
    @NotBlank(message = "This field must not be empty")
    private String content;
    private EventEntity event;

    public UserEntity getName() {
        return name;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    public void setName(UserEntity name) {
        this.name = name;
    }
}
