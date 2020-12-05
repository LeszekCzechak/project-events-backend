package pl.sdacademy.projecteventsbackend.comment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sdacademy.projecteventsbackend.comment.dto.CommentResponse;
import pl.sdacademy.projecteventsbackend.comment.dto.CommentResponseByEvent;
import pl.sdacademy.projecteventsbackend.comment.dto.EditCommentRequest;
import pl.sdacademy.projecteventsbackend.comment.dto.NewCommentRequest;
import pl.sdacademy.projecteventsbackend.component.userContext.UserContext;
import pl.sdacademy.projecteventsbackend.event.EventEntity;
import pl.sdacademy.projecteventsbackend.event.EventService;
import pl.sdacademy.projecteventsbackend.exception.CommentNotFoundException;
import pl.sdacademy.projecteventsbackend.user.model.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserContext userContext;
    private final EventService eventService;

    public CommentService(CommentRepository commentRepository, UserContext userContext, EventService eventService) {
        this.commentRepository = commentRepository;
        this.userContext = userContext;
        this.eventService = eventService;
    }

    @Transactional
    public CommentResponse UpdateCommentById(long commentId, EditCommentRequest editComment) {
        CommentEntity commentEntity = commentRepository.getOne(commentId);
        commentEntity.setContent(editComment.getContent());
        commentEntity.setUpdateOn(LocalDateTime.now());
        commentRepository.save(commentEntity);

        return new CommentResponse(commentEntity.getId(), commentEntity.getCreatedBy(), commentEntity.getContent(), commentEntity.getCreatedOn());
    }

    public void DeleteCommentById(long commentId) throws CommentNotFoundException {
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        commentRepository.deleteById(commentId);
    }


    public void addNewComment(NewCommentRequest newComment) {

        UserEntity currentUser = userContext.getCurrentUser();
        EventEntity currentEvent = eventService.getEventById(newComment.getEventId());

        CommentEntity commentEntity=new CommentEntity();
        commentEntity.setContent(newComment.getContent());
        commentEntity.setCreatedBy(currentUser);
        commentEntity.setEvent(currentEvent);
        commentEntity.setCreatedOn(LocalDateTime.now());
        commentEntity.setUpdateOn(LocalDateTime.now());

        commentRepository.save(commentEntity);

    }

    public List<CommentEntity> getAllComment() {
        return commentRepository.findAll();
    }

    public List<CommentResponseByEvent> findCommentByEventId(long eventId) {
        List<CommentEntity> commentEntities = commentRepository.findAllByEventId(eventId);
        return commentEntities.stream()
                .map(comment -> {
                    CommentResponseByEvent commentResponseByEvent = new CommentResponseByEvent(comment.getCreatedBy().getUsername(),
                            comment.getContent());
                    return commentResponseByEvent;
                }).collect(Collectors.toList());
    }
}
