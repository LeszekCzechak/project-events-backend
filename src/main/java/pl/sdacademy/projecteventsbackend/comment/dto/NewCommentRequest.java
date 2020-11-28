package pl.sdacademy.projecteventsbackend.comment.dto;

public class NewCommentRequest {

    private long eventId;
    private String content;

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
