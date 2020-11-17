package pl.sdacademy.projecteventsbackend.user.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class EditUserRequest {

    @NotBlank(message = "This field must not be empty")
    @Size(min = 3,max = 20,message = "Min 3 signs, max is 20")
    private String name;
    @Email(message = "Invalid email ")
    private String mail;
    @DateTimeFormat(style = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
