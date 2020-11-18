package pl.sdacademy.projecteventsbackend.exception;

public class CommentNotFoundException extends Exception{
    private static final String EXCEPTION = "Comment not found";

    public CommentNotFoundException() {
        super(EXCEPTION);
    }
}
