package pl.sdacademy.projecteventsbackend.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Wrong Password or Login");
    }
}
