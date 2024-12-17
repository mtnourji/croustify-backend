package com.example.rollingFoods.rollingFoodsApp.models;


import com.example.rollingFoods.rollingFoodsApp.models.embedded.Address;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "location_site")
public class LocationSite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_location_site")
    private Long id;
    private String name;
    private String description;
    private Float length;
    private Float width;
    private Address address;
    @Column(name = "create_at")
    private Date createdDate;

    //Relation ManyToOne avec Proprio car un locationSite appartient à un proprio
    @ManyToOne
    @JoinColumn(name = "id_location_owner")
    @JsonBackReference
    private LocationOwner locationOwner;

    public LocationSite() {
    }

    public LocationSite(Long id, String name, String description, Float length, Float width, Address address) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.length = length;
        this.width = width;
        this.address = address;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public LocationOwner getProprio() {
        return locationOwner;
    }

    public void setProprio(LocationOwner locationOwner) {this.locationOwner = locationOwner;}
}
