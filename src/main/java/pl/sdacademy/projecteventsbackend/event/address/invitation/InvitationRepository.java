package pl.sdacademy.projecteventsbackend.event.address.invitation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<InvitationEntity, Long> {

    //wszystkie zaproszenie gdzie eventId= eventId oraz boolean sendingStatus=true
    public List<InvitationEntity> findAllByEventIdAndSendingStatusIsTrue(Long eventId);


}
