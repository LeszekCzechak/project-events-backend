package pl.sdacademy.projecteventsbackend.event;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.sdacademy.projecteventsbackend.component.mailService.MailService;
import pl.sdacademy.projecteventsbackend.component.userContext.UserContext;
import pl.sdacademy.projecteventsbackend.event.address.AddressEntity;
import pl.sdacademy.projecteventsbackend.event.address.AddressRepository;
import pl.sdacademy.projecteventsbackend.event.dto.CreateEventRequest;
import pl.sdacademy.projecteventsbackend.event.dto.EventResponse;
import pl.sdacademy.projecteventsbackend.event.dto.InvitationRequest;
import pl.sdacademy.projecteventsbackend.exception.EventNameNotFoundException;
import pl.sdacademy.projecteventsbackend.external.geocodingApi.GeocodingApiClient;
import pl.sdacademy.projecteventsbackend.external.geocodingApi.model.LocationCoordinates;
import pl.sdacademy.projecteventsbackend.user.UserRepository;
import pl.sdacademy.projecteventsbackend.user.model.UserEntity;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class EventService {

    private final AddressRepository addressRepository;
    private final EventRepository eventRepository;
    private final UserContext userContext;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final GeocodingApiClient geocodingApiClient;

    public EventService(AddressRepository addressRepository, EventRepository eventRepository, UserContext userContext, UserRepository userRepository, MailService mailService, GeocodingApiClient geocodingApiClient) {
        this.addressRepository = addressRepository;
        this.eventRepository = eventRepository;
        this.userContext = userContext;
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.geocodingApiClient = geocodingApiClient;
    }

    public List<EventResponse> getAllEvents() {
        List<EventEntity> eventEntities = eventRepository.findAll();

        List<EventResponse> response = eventEntities.stream()
                .map(eventEntity -> {
                    EventResponse eventResponse = new EventResponse(
                            eventEntity.getId(),
                            eventEntity.getName(),
                            eventEntity.getDescription(),
                            eventEntity.getEventStart(),
                            eventEntity.getAddress().getCity(),
                            eventEntity.getAddress().getStreet(),
                            eventEntity.getAddress().getZipcode(),
                            eventEntity.getOrganizer().getUsername());
                    return eventResponse;
                }).collect(Collectors.toList());
        return response;
    }

    public EventEntity getEventByName(String name) {
        return eventRepository.findByName(name).orElseThrow(
                EventNameNotFoundException::new
        );
    }

    @Transactional
    public EventResponse addNewEvent(CreateEventRequest newEvent) {
        UserEntity currentUser = userContext.getCurrentUser();

        if (!currentUser.getEnabled()) {
            throw new AccessDeniedException("You can't do that");
        }

        String location= newEvent.getStreet().trim()+" "+newEvent.getZipcode().trim()+" "+newEvent.getCity().trim();

        LocationCoordinates locationCoordinates = geocodingApiClient.getLocationCoordinates(location);

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity(newEvent.getCity());
        addressEntity.setStreet(newEvent.getStreet());
        addressEntity.setZipcode(newEvent.getZipcode());
        addressEntity.setFormattedAddress(locationCoordinates.getFormattedAddress());
        addressEntity.setLat(locationCoordinates.getLat());
        addressEntity.setLng(locationCoordinates.getLng());



        EventEntity eventEntity = new EventEntity();
        eventEntity.setName(newEvent.getEventName());
        eventEntity.setDescription(newEvent.getDescription());
        eventEntity.setAddress(addressEntity);
        eventEntity.setOrganizer(currentUser);
        eventEntity.setEventStart(newEvent.getEventStart());

        EventResponse response = new EventResponse();
        response.setCity(eventEntity.getAddress().getCity());
        response.setStreet(eventEntity.getAddress().getStreet());
        response.setZipcode(eventEntity.getAddress().getZipcode());
        response.setDescription(eventEntity.getDescription());
        response.setEventName(eventEntity.getName());
        response.setOrganizerName(eventEntity.getOrganizer().getUsername());
        response.setEventStart(eventEntity.getEventStart());

        addressRepository.save(addressEntity);
        eventRepository.save(eventEntity);

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

    public EventEntity getEventById(long id) {
        return eventRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Event not found"));//TODO create new exception
    }


    @Transactional
    public void sendInvitation(InvitationRequest request) {

        EventEntity event = eventRepository.findById(request.getEventId()).orElseThrow(
                () -> new EventNameNotFoundException());

        String mail= request.getMail();

        if (userContext.getCurrentUser().getId() != event.getOrganizer().getId()) {
            throw new EventNameNotFoundException();//TODO change exception
        }

        UserEntity guest = userRepository.findUserEntityByMail(mail).orElseThrow(() -> new UsernameNotFoundException("User not found"));


        String name = guest.getUsername();
        String textToUuid = "" + name + event.getId();
        String uuidGuest = UUID.nameUUIDFromBytes(textToUuid.getBytes()).toString();
        try {
            String invitationLink = "http://localhost:8080/invitation" + "/" + uuidGuest;

            String url = "<a href=\"" + invitationLink + "\">Aktywuj</a>";


            mailService.sendMail(guest.getMail(),
                    "Invitation to event",
                    "Hi " + name + ",\n" +
                            userContext.getCurrentUser().getUsername() + "invaiting you to" +
                            event.getName() + "\n" +
                            event.getEventStart() + "\n" +
                            event.getDescription() + "\n" +
                            event.getAddress() + "\n" +
                            "Click link to answer invitation.\n"
                            + url,
                    true);
        } catch (MessagingException e) {
        }

    }
}
