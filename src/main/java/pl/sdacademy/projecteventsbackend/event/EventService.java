package pl.sdacademy.projecteventsbackend.event;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import pl.sdacademy.projecteventsbackend.component.userContext.UserContext;
import pl.sdacademy.projecteventsbackend.exception.EventNameNotFoundException;
import pl.sdacademy.projecteventsbackend.user.model.UserEntity;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final UserContext userContext;

    public EventService(EventRepository eventRepository, UserContext userContext) {
        this.eventRepository = eventRepository;
        this.userContext = userContext;
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

    @Transactional
    public List<EventEntity> findAllByAdress_City(String city) {
        UserEntity currentUser = userContext.getCurrentUser();

        if(currentUser.getEnabled() == false){
                throw new AccessDeniedException("Brak dostępu");
        }
        List<EventEntity> events = eventRepository.findAllByAddress_City(city)
                .orElseThrow(()->new EventNameNotFoundException());
        return  events;
    }
}
