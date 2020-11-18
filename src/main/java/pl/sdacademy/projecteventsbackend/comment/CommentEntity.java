package pl.sdacademy.projecteventsbackend.comment;

import pl.sdacademy.projecteventsbackend.event.EventEntity;
import pl.sdacademy.projecteventsbackend.user.model.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CommentEntity {

    //user name, user id, event id, event name, content (nullable or „0” by default) - LocalDateTime createdOn; - LocalDateTime updatedOn;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private EventEntity eventId;
    @ManyToOne
    private UserEntity name;
    private LocalDateTime createdOn;
    private LocalDateTime updateOn;
    private String content = null;


    public CommentEntity() {

    }

    public CommentEntity(Long id, EventEntity eventId, UserEntity name) {
        this.id = id;
        this.eventId = eventId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventEntity getEventId() {
        return eventId;
    }

    public void setEventId(EventEntity eventId) {
        this.eventId = eventId;
    }

    public UserEntity getName() {
        return name;
    }

    public void setName(UserEntity name) {
        this.name = name;
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
