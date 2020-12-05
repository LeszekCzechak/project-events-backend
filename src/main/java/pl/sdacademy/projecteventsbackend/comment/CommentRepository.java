package pl.sdacademy.projecteventsbackend.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sdacademy.projecteventsbackend.comment.dto.CommentResponseByEvent;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAllByEventId(long eventId);
}
