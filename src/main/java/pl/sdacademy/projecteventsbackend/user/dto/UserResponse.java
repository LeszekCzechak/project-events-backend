package pl.sdacademy.projecteventsbackend.user.dto;

import java.time.LocalDate;

public class UserResponse {

    private Long id;
    private String username;
    private String mail;
    private LocalDate dateOfBirth;

    public UserResponse(Long id, String username, String mail, LocalDate dateOfBirth) {
        this.id = id;
        this.username = username;
        this.mail = mail;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
