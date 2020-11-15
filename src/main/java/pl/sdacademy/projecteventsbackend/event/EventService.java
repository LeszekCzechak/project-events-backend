package pl.sdacademy.projecteventsbackend.event;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<EventEntity> getAllEvents (){
        return eventRepository.findAll();
    }
    public EventEntity getEvent (String name){
        return eventRepository.findByName(name);
    }

}
