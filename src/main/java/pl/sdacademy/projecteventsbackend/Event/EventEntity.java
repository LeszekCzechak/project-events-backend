package pl.sdacademy.projecteventsbackend.Event;

import pl.sdacademy.projecteventsbackend.User.UserEntity;

import javax.persistence.*;

@Entity
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private AddressEntity address;


    public EventEntity(String name, String description, AddressEntity address) {
        this.name = name;
        this.description = description;
        this.address = address;
    }

    public EventEntity() {
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }
}
