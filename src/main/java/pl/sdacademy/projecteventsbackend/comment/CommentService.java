package pl.sdacademy.projecteventsbackend.comment;

import org.springframework.transaction.annotation.Transactional;
import pl.sdacademy.projecteventsbackend.comment.dto.CommentResponse;
import pl.sdacademy.projecteventsbackend.comment.dto.EditCommentRequest;
import pl.sdacademy.projecteventsbackend.exception.CommentNotFoundException;
import pl.sdacademy.projecteventsbackend.user.dto.UserResponse;

import java.time.LocalDateTime;

public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public void UpdateCommentById(long commentId, EditCommentRequest editComment) {
        CommentEntity commentEntity = commentRepository.getOne(commentId);
        commentEntity.setName(editComment.getName());
        commentEntity.setContent(editComment.getContent());
        commentEntity.setUpdateOn(LocalDateTime.now());
        commentRepository.save(commentEntity);

        CommentResponse commentResponse = new CommentResponse(commentEntity.getId(), commentEntity.getName(), commentEntity.getContent());

    }

    public void DeleteCommentById(long commentId, EditCommentRequest editComment) throws CommentNotFoundException {
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
    }
}
