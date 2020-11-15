package pl.sdacademy.projecteventsbackend.Event;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    public List<EventEntity> getAllEvents (){
        return eventService.getAllEvents();
    }


    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public EventEntity getEventByName(@PathVariable String name) {
        return eventService.getEvent(name);
    }

}
