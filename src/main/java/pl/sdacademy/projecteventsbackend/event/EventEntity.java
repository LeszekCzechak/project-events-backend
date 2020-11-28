package pl.sdacademy.projecteventsbackend.event;

import pl.sdacademy.projecteventsbackend.event.address.AddressEntity;
import pl.sdacademy.projecteventsbackend.user.model.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private AccessToEvent accessToEvent;

    @ManyToOne
//    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private UserEntity organizer;

    private LocalDateTime eventStart;


    public EventEntity(String name, String description,
                       AddressEntity address, UserEntity organizer,
                       LocalDateTime eventStart, AccessToEvent accessToEvent) {
        this.name = name;
        this.description = description;
        this.accessToEvent = accessToEvent;
        this.address = address;
        this.organizer = organizer;
        this.eventStart = eventStart;

    }

    public EventEntity() {
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public void setOrganizer(UserEntity organizerId) {
        this.organizer = organizerId;
    }
    public AccessToEvent getAccessToEvent() {
        return accessToEvent;
    }

    public void setAccessToEvent(AccessToEvent accessToEvent) {
        this.accessToEvent = accessToEvent;
    }


    private enum AccessToEvent {
        PUBLIC,
        PRIVATE
    }

    public LocalDateTime getEventStart() {
        return eventStart;
    }

    public void setEventStart(LocalDateTime eventStart) {
        this.eventStart = eventStart;
    }

    public UserEntity getOrganizer() {
        return organizer;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
