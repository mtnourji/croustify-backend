package com.example.rollingFoods.rollingFoodsApp.models;


import com.example.rollingFoods.rollingFoodsApp.dto.AddressDTO;
import com.example.rollingFoods.rollingFoodsApp.models.embedded.Address;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;


@MappedSuperclass
public abstract class User {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    @Column(name="phone_number")
    private String phoneNumber;
    private String urlProfilePicture;


    @Embedded
    private Address address;
    @Column(name = "create_at")
    private LocalDate createdDate = LocalDate.now();
    @Column(name = "update_at")
    private LocalDate updatedDate = LocalDate.now();


    public User() {
    }

    public User(Long id, String firstname, String lastname, String phoneNumber, Address address, LocalDate createdDate, LocalDate updatedDate) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }


    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUrlProfilePicture() {
        return urlProfilePicture;
    }

    public void setUrlProfilePicture(String urlProfilePicture) {
        this.urlProfilePicture = urlProfilePicture;
    }


}
