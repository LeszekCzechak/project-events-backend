package pl.sdacademy.projecteventsbackend.event.dto;

public class InvitationRequest {

   private String mail;
   private long eventId;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }
}
