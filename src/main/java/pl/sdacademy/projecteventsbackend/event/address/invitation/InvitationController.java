package pl.sdacademy.projecteventsbackend.event.address.invitation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sdacademy.projecteventsbackend.event.address.invitationDto.InvitationResponse;

import java.util.List;

@Controller
@RequestMapping("/invitation")
public class InvitationController {
    private InvitationService invitationService;

//     public String isInvitationSend(){
//         return invitationService.sendingStatusResponse();
//     }

     @GetMapping("/approved/{id}")
     public HttpStatus invitationApproved(@PathVariable Long id) {
        invitationService.setInvitationResponseStatus(id,true);
        return HttpStatus.ACCEPTED;
     }

     @GetMapping("/decline/{id}")
     public HttpStatus invitationDecline(@PathVariable Long id) {
         invitationService.setInvitationResponseStatus(id, false);
         return HttpStatus.OK;
     }

     @GetMapping("/eventinfo/accepted/{eventId}")
    public List<InvitationResponse> guestsList(@PathVariable Long eventId) {
         return invitationService.getGuestsList(eventId);
     }
}
