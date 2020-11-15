package pl.sdacademy.projecteventsbackend.Event;

import javax.persistence.*;
import java.util.List;

@Entity
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String street;
    private String city;
    private int zipcode;


    public AddressEntity() {
    }

    public AddressEntity(long id, String street, String city, int zipcode) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
    }

    public long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public int getZipcode() {
        return zipcode;
    }
}
