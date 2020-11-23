package pl.sdacademy.projecteventsbackend.event;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import pl.sdacademy.projecteventsbackend.component.userContext.UserContext;
import pl.sdacademy.projecteventsbackend.event.dto.CreateEventRequest;
import pl.sdacademy.projecteventsbackend.event.dto.CreateEventResponse;
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

    @Transactional
    public CreateEventResponse addNewEvent(CreateEventRequest newEvent) {

        UserEntity currentUser= userContext.getCurrentUser();

        if(!currentUser.getEnabled()) {
            throw new AccessDeniedException("You can't do that");
        }

        AddressEntity addressEntity=new AddressEntity();
        addressEntity.setCity(newEvent.getCity());
        addressEntity.setStreet(newEvent.getStreet());
        addressEntity.setZipcode(newEvent.getZipcode());


        EventEntity eventEntity = new EventEntity();
        eventEntity.setName(newEvent.getEventName());
        eventEntity.setDescription(newEvent.getDescription());
        eventEntity.setAddress(addressEntity);
        eventEntity.setOrganizer(currentUser);

        eventRepository.save(eventEntity);

        CreateEventResponse response=new CreateEventResponse();
        response.setCity(eventEntity.getAddress().getCity());
        response.setStreet(eventEntity.getAddress().getStreet());
        response.setZipcode(eventEntity.getAddress().getZipcode());
        response.setDescription(eventEntity.getDescription());
        response.setEventName(eventEntity.getName());
        response.setOrganizerName(eventEntity.getOrganizer().getUsername());

        return response;
    }

    @Transactional
    public List<EventEntity> findAllByAdress_City(String city) {
        UserEntity currentUser = userContext.getCurrentUser();

        if (currentUser.getEnabled() == false) {
            throw new AccessDeniedException("Brak dostępu");
        }
        List<EventEntity> events = eventRepository.findAllByAddress_City(city)
                .orElseThrow(() -> new EventNameNotFoundException());
        return events;
    }
}
