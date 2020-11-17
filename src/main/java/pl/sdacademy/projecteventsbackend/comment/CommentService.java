package pl.sdacademy.projecteventsbackend.comment;

import java.util.List;

public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<CommentEntity> getAllComments(){
        return commentRepository.findAll();

    }
}
