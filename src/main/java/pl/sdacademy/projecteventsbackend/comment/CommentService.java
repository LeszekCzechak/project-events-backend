package pl.sdacademy.projecteventsbackend.comment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sdacademy.projecteventsbackend.comment.dto.CommentResponse;
import pl.sdacademy.projecteventsbackend.comment.dto.EditCommentRequest;
import pl.sdacademy.projecteventsbackend.exception.CommentNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public CommentResponse UpdateCommentById(long commentId, EditCommentRequest editComment) {
        CommentEntity commentEntity = commentRepository.getOne(commentId);
        commentEntity.setContent(editComment.getContent());
        commentEntity.setUpdateOn(LocalDateTime.now());
        commentRepository.save(commentEntity);

        return new CommentResponse(commentEntity.getId(), commentEntity.getCreatedBy(), commentEntity.getContent());
    }

    public void DeleteCommentById(long commentId) throws CommentNotFoundException {
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        commentRepository.deleteById(commentId);
    }


    public CommentEntity addNewComment(CommentEntity newComment) {
        return commentRepository.save(newComment);
    }

    public List<CommentEntity> getAllComment() {
        return commentRepository.findAll();
    }

}
