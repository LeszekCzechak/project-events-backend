package pl.sdacademy.projecteventsbackend.event;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    public List<EventEntity> getAllEvents() {
        return eventService.getAllEvents();
    }


    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public EventEntity getEventByName(@PathVariable String name) {
        return eventService.getEventByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventEntity addNewEvent(@RequestBody EventEntity newEvent) {
        return eventService.addNewEvent(newEvent);
    }

    @GetMapping("/city/{city}")
    @ResponseStatus(HttpStatus.OK)
    public List<EventEntity> findAllByAdress_City(@PathVariable String city) {
        return eventService.findAllByAdress_City(city);
    }
}
