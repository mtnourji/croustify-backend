package com.example.rollingFoods.rollingFoodsApp.models;


import jakarta.persistence.*;

@Entity
@Table(name = "favorite")
public class Favorite {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_credential_id")
    private UserCredential userCredential;

    @ManyToOne
    private FoodTruck foodTruck;

    public Favorite() {
    }

    public Favorite(Long id, UserCredential userCredential, FoodTruck foodTruck) {
        this.id = id;
        this.userCredential = userCredential;
        this.foodTruck = foodTruck;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserCredential getUserCredential() {
        return userCredential;
    }

    public void setUserCredential(UserCredential userCredential) {
        this.userCredential = userCredential;
    }

    public FoodTruck getFoodTruck() {
        return foodTruck;
    }

    public void setFoodTruck(FoodTruck foodTruck) {
        this.foodTruck = foodTruck;
    }


}
