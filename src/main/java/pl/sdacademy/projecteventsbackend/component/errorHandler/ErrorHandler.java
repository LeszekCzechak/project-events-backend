package pl.sdacademy.projecteventsbackend.component.errorHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.sdacademy.projecteventsbackend.exception.CommentNotFoundException;
import pl.sdacademy.projecteventsbackend.exception.EventNameNotFoundException;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCommentNotFoundException(CommentNotFoundException e){
        ErrorResponse errorResponse= new ErrorResponse(e);
        return createResponseEntity(errorResponse);
    }

    @ExceptionHandler(EventNameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEventNameNotFoundException(EventNameNotFoundException e){
        ErrorResponse errorResponse= new ErrorResponse(e);
        return createResponseEntity(errorResponse);
    }

    private ResponseEntity<ErrorResponse> createResponseEntity(ErrorResponse errorResponse) {
        return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
    }

}
