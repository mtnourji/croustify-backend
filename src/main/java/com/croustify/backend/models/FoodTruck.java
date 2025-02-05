package com.croustify.backend.models;


import com.croustify.backend.models.embedded.Coordinates;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "food_truck")

public class FoodTruck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_food_truck")
    private Long id;
    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "food_truck_category",
            joinColumns = @JoinColumn(name = "food_truck_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    private String speciality;
    private String profileImage;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "longitude"))
    })
    private Coordinates coordinates;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "default_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "default_longitude"))
    })
    private Coordinates defaultCoordinates;
    private Float length;
    private Float width;
    private Boolean isOpen = false;
    @Column(name = "rating")
    private int rating = 0;
    @Column(name = "rating_count")
    private int ratingCount = 0;
    @Column(name = "created_date")
    private LocalDate createdDate = LocalDate.now();

    //Relation ManyToOne avec FoodTruckOwner car un foodTruck appartient à un foodTruckOwner
    @ManyToOne
    @JoinColumn(name = "id_food_truck_owner")
    @JsonBackReference
    private FoodTruckOwner foodTruckOwner;

    //Relation OneToMany avec Menu car un foodTruck peut contenir plusieurs menus
    @OneToMany(mappedBy ="foodTruck", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Menu> menus = new HashSet<>();

    //Relation OneToMany avec Picture car un foodTruck peut contenir plusieurs images
    @OneToMany(mappedBy = "foodTruck", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Picture> pictures = new ArrayList<>();

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

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public FoodTruckOwner getFoodTruckOwner() {
        return foodTruckOwner;
    }

    public void setFoodTruckOwner(FoodTruckOwner foodTruckOwner) {
        this.foodTruckOwner = foodTruckOwner;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Coordinates getDefaultCoordinates() {
        return defaultCoordinates;
    }

    public void setDefaultCoordinates(Coordinates defaultCoordinates) {
        this.defaultCoordinates = defaultCoordinates;
    }
}
