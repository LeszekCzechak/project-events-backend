package pl.sdacademy.projecteventsbackend.event.address;

import javax.persistence.*;
@Entity
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String street;
    private String city;
    private String zipcode;


    public AddressEntity() {
    }

    public AddressEntity(long id, String street, String city, String zipcode) {
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

    public String getZipcode() {
        return zipcode;
    }

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
