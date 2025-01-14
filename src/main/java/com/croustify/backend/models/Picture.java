package com.croustify.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "picture")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_picture")
    private Long id;
    private String name;
    private String location;

    //Relation ManyToOne avec FoodTruck car une image appartient à un foodTruck
    @ManyToOne
    @JoinColumn(name = "id_food_truck")
    private FoodTruck foodTruck;


    public Picture() {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public FoodTruck getFoodTruck() {
        return foodTruck;
    }

    public void setFoodTruck(FoodTruck foodTruck) {
        this.foodTruck = foodTruck;
    }
}
