package pl.sdacademy.projecteventsbackend.comment;

import pl.sdacademy.projecteventsbackend.event.EventEntity;
import pl.sdacademy.projecteventsbackend.user.model.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private EventEntity event;
    @ManyToOne
    private UserEntity createdBy;
    private LocalDateTime createdOn;
    private LocalDateTime updateOn;
    private String content = null;


    public CommentEntity() {

    }

    public CommentEntity(Long id, EventEntity event, UserEntity createdBy) {
        this.id = id;
        this.event = event;
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity eventId) {
        this.event = eventId;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdateOn() {
        return updateOn;
    }

    public void setUpdateOn(LocalDateTime updateOn) {
        this.updateOn = updateOn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
