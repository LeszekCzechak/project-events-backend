package pl.sdacademy.projecteventsbackend.Event;

import org.springframework.stereotype.Service;
import pl.sdacademy.projecteventsbackend.exception.EventNameNotFoundException;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<EventEntity> getAllEvents() {
        return eventRepository.findAll();
    }

    public EventEntity getEventByName(String name) {
        return eventRepository.findByName(name).orElseThrow(
                EventNameNotFoundException::new
        );
    }

    public EventEntity addNewEvent(EventEntity newEvent) {
        return eventRepository.save(newEvent);
    }
}
