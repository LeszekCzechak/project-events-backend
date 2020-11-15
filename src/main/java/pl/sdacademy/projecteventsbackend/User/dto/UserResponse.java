package pl.sdacademy.projecteventsbackend.User.dto;

public class UserResponse {

    private String username;
    private String mail;

    public UserResponse(String username, String mail) {
        this.username = username;
        this.mail = mail;
    }

}
