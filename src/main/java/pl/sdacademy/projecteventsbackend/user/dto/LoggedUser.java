package pl.sdacademy.projecteventsbackend.user.dto;

public class LoggedUser {
    String username;

    public LoggedUser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
