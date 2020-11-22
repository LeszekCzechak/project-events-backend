package pl.sdacademy.projecteventsbackend.comment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.sdacademy.projecteventsbackend.comment.dto.CommentResponse;
import pl.sdacademy.projecteventsbackend.comment.dto.EditCommentRequest;
import pl.sdacademy.projecteventsbackend.exception.CommentNotFoundException;

import java.util.List;


@RestController
@RequestMapping("/comment")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentEntity addNewComment(@RequestParam CommentEntity newComment){
        return commentService.addNewComment(newComment);
    }

    @DeleteMapping("/id")
    public void deleteComment(@PathVariable ("id") long commentId) throws CommentNotFoundException {
        commentService.DeleteCommentById(commentId);
    }

    @PutMapping("/id")
    public CommentResponse editComment(@PathVariable ("id") long commentId, EditCommentRequest editComment) throws CommentNotFoundException {
        return commentService.UpdateCommentById(commentId, editComment);
    }

    public List<CommentEntity> getAllComment() {
        return commentService.getAllComment();
    }


}
