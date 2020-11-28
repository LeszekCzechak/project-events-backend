package pl.sdacademy.projecteventsbackend.event.dto;

import java.time.LocalDateTime;

public class EventResponse {

    private Long id;
    private String eventName;
    private String description;
    private LocalDateTime eventStart;
    private String street;
    private String city;
    private String zipcode;
    private String organizerName;

    public EventResponse() {
    }

    public EventResponse(Long id, String eventName, String description, LocalDateTime eventStart, String street, String city, String zipcode, String organizerName) {
        this.id = id;
        this.eventName = eventName;
        this.description = description;
        this.eventStart = eventStart;
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
        this.organizerName = organizerName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getEventStart() {
        return eventStart;
    }

    public void setEventStart(LocalDateTime eventStart) {
        this.eventStart = eventStart;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
