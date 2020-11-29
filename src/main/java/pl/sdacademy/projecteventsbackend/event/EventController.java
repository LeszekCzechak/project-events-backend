package pl.sdacademy.projecteventsbackend.event;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sdacademy.projecteventsbackend.event.dto.CreateEventRequest;
import pl.sdacademy.projecteventsbackend.event.dto.EventResponse;
import pl.sdacademy.projecteventsbackend.event.dto.InvitationRequest;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventResponse> getAllEvents() {
        return eventService.getAllEvents();
    }


    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public EventEntity getEventByName(@PathVariable String name) {
        return eventService.getEventByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse addNewEvent(@RequestBody CreateEventRequest newEvent) {
        EventResponse response = eventService.addNewEvent(newEvent);
        return response;
    }


    @GetMapping("/city/{city}")
    @ResponseStatus(HttpStatus.OK)
    public List<EventEntity> findAllByAdress_City(@PathVariable String city) {
        return eventService.findAllByAdress_City(city);
    }

    @PostMapping("/invitation/send")
    public ResponseEntity<Void> invitePeople(@RequestBody InvitationRequest invitationRequest) {
        eventService.sendInvitation(invitationRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
