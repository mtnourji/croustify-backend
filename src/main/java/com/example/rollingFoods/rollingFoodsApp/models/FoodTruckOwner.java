package com.example.rollingFoods.rollingFoodsApp.models;


import com.example.rollingFoods.rollingFoodsApp.models.embedded.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;
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



    //Relation OneToMany avec FoodTruck car un foodTruckOwner peut avoir plusieurs foodTruck
    @OneToMany(mappedBy = "foodTruckOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FoodTruck> foodTrucks = new HashSet<>();

    //Relation OneToOne avec UserCredential car un foodTruckOwner a un userCredential
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserCredential userCredential;


    public FoodTruckOwner() {
    }

    public FoodTruckOwner(Long id, String firstname, String lastname, String phoneNumber, String email, Address address, LocalDate createdDate, LocalDate updatedDate, String companyName, String tva, String bankNumber, Set<FoodTruck> foodTrucks, UserCredential userCredential) {
        super(id, firstname, lastname, phoneNumber, address, createdDate, updatedDate);
        this.companyName = companyName;
        this.tva = tva;
        this.bankNumber = bankNumber;
        this.foodTrucks = foodTrucks;
        this.userCredential = userCredential;
    }


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


}
