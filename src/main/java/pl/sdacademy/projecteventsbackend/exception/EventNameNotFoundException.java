package pl.sdacademy.projecteventsbackend.exception;

public class EventNameNotFoundException extends RuntimeException {
    private static final String EXCEPTION_MESSAGE = "That name event not expected";

    public EventNameNotFoundException() {
        super(EXCEPTION_MESSAGE);
    }
}
