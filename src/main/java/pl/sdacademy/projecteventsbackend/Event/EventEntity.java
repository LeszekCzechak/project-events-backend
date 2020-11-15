package pl.sdacademy.projecteventsbackend.Event;

import pl.sdacademy.projecteventsbackend.User.UserEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String address;


    public EventEntity(String name, String description, String address) {
        this.name = name;
        this.description = description;
        this.address = address;
    }

    public EventEntity() {
    }
}
