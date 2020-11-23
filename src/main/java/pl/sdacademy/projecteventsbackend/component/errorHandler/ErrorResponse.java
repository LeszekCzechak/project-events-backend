package pl.sdacademy.projecteventsbackend.component.errorHandler;

import org.springframework.http.HttpStatus;
import pl.sdacademy.projecteventsbackend.exception.CommentNotFoundException;

public class ErrorResponse {
    private final int statusCode;
    private final  String message;

    public ErrorResponse(Exception exception){
        this(500, exception.getMessage());
    }

    public ErrorResponse(CommentNotFoundException commentNotFoundException){
        this.statusCode = HttpStatus.BAD_REQUEST.value();
        this.message = commentNotFoundException.getMessage();
    }


    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
