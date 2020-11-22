package pl.sdacademy.projecteventsbackend.event.dto;


import pl.sdacademy.projecteventsbackend.event.EventController;

import java.time.LocalDateTime;

public class CreateEventRequest {
    /*
    long createdById; String eventName;
    String description; LocalDateTime eventStart;
    String street; String city; int/String zip-code;
     */
    private long createsdById;
    private String eventName;
    private String description;
    private LocalDateTime eventStart;
    private String street;
    private String city;
    private int zipcode;

    public EventController addNewEvent(){
        return null;
    }

}
