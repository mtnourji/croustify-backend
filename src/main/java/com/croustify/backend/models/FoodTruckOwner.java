package com.croustify.backend.models;


import jakarta.persistence.*;

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

    @OneToMany(mappedBy = "foodTruckOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FoodTruck> foodTrucks = new HashSet<>();

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

    public int getNumberOfAllowedFoodTrucks() {
        return numberOfAllowedFoodTrucks;
    }

    public void setNumberOfAllowedFoodTrucks(int numberOfAllowedFoodTrucks) {
        this.numberOfAllowedFoodTrucks = numberOfAllowedFoodTrucks;
    }
}
