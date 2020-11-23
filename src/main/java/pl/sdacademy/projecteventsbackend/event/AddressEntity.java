package pl.sdacademy.projecteventsbackend.event;

import javax.persistence.*;
import java.util.Set;

@Entity
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String street;
    private String city;
    private String zipcode;
    //private Set<Long> eventId;


    public AddressEntity() {
    }

    public AddressEntity(long id, String street, String city, String zipcode /*Set<Long> eventId*/) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
        //this.eventId = eventId;
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

    public String getZipcode() {
        return zipcode;
    }
    //public Set<Long> getSet() { return eventId; }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
