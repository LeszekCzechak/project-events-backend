package pl.sdacademy.projecteventsbackend.event.address.invitation;

import org.springframework.stereotype.Service;
import pl.sdacademy.projecteventsbackend.event.address.invitationDto.InvitationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvitationService {
    private final InvitationRepository invitationRepository;

    public InvitationService(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }
//
//    public String sendingStatusResponse( InvitationEntity sendingStatus) {
//        String response;
//        if(sendingStatus == null){
//            response = "Didn't get the answer";
//        } else (sendingStatus =! true) {
//
//        }
//    }

    public void setInvitationResponseStatus(Long id, boolean sendingStatus) {
        InvitationEntity invitationEntity = invitationRepository.findById(id).orElseThrow(() -> new RuntimeException());
        invitationEntity.setSendingStatus(sendingStatus);
        invitationRepository.save(invitationEntity);

    }

    public List<InvitationResponse> getGuestsList(Long eventId) {
        List<InvitationEntity> response = invitationRepository.findAllByEventIdAndSendingStatusIsTrue(eventId);
       return response
                .stream()
                .map(x->{
                    InvitationResponse invitationResponse = new InvitationResponse();
                    invitationResponse.setUsername(x.getGuestName());
                    return invitationResponse;
                })
                .collect(Collectors.toList());
    }
}
