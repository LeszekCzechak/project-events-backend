package pl.sdacademy.projecteventsbackend.user.dto;

public class UserResponse {

    private String username;
    private String mail;

    public UserResponse(String username, String mail) {
        this.username = username;
        this.mail = mail;
    }

}
