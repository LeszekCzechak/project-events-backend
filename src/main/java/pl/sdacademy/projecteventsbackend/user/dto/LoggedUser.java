package pl.sdacademy.projecteventsbackend.user.dto;

public class LoggedUser {
    String name;

    public LoggedUser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
