package pl.sdacademy.projecteventsbackend.event.address.invitation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class InvitationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // jeżeli można to powinno się robić to obiektowe
    private Long organizerId;
    private Long eventId;
    private String organizerName;
    private String guestName;
    private boolean sendingStatus;
    /* kto zaprosił, kogo i na co zaprosił
    czy zaproszenie zostanie przyjęte boolean - null - nie dostał odp, false - spier*alaj, - true - idziemy chlac
    potem co widzi:
    null - zaproszenia wysłane,
    false - pieprz sie,
    true - obczaj gości, zobacz jak bedzie zajebiście


    zapproszenia mają swoje id long
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Long organizerId) {
        this.organizerId = organizerId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public boolean isSendingStatus() {
        return sendingStatus;
    }

    public void setSendingStatus(boolean sendingStatus) {
        this.sendingStatus = sendingStatus;
    }
}

