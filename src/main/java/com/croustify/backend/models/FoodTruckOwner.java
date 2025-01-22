package com.croustify.backend.models;


import com.croustify.backend.models.embedded.Address;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "food_truck_owner")
public class FoodTruckOwner extends User {

    @Column(name = "company_name")
    private String companyName;
    @Column(name = "tva_number")
    private String tva;
    @Column(name = "bank_number")
    private String bankNumber;
    private int numberOfAllowedFoodTrucks;



    //Relation OneToMany avec FoodTruck car un foodTruckOwner peut avoir plusieurs foodTruck
    @OneToMany(mappedBy = "foodTruckOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FoodTruck> foodTrucks = new HashSet<>();

    //Relation OneToOne avec UserCredential car un foodTruckOwner a un userCredential
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserCredential userCredential;


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTva() {
        return tva;
    }

    public void setTva(String tva) {
        this.tva = tva;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public Set<FoodTruck> getFoodTrucks() {
        return foodTrucks;
    }

    public void setFoodTrucks(Set<FoodTruck> foodTrucks) {
        this.foodTrucks = foodTrucks;
    }

    public UserCredential getUserCredential() {
        return userCredential;
    }

    public void setUserCredential(UserCredential userCredential) {
        this.userCredential = userCredential;
    }


    public int getNumberOfAllowedFoodTrucks() {
        return numberOfAllowedFoodTrucks;
    }

    public void setNumberOfAllowedFoodTrucks(int numberOfAllowedFoodTrucks) {
        this.numberOfAllowedFoodTrucks = numberOfAllowedFoodTrucks;
    }
}
