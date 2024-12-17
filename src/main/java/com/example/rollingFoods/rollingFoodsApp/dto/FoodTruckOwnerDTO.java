package com.example.rollingFoods.rollingFoodsApp.dto;

import com.example.rollingFoods.rollingFoodsApp.models.embedded.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


public class FoodTruckOwnerDTO  extends UserDTO{
    private Long id;
    private String companyName;
    private String tva;
    private String bankNumber;

    public FoodTruckOwnerDTO() {
    }

    public FoodTruckOwnerDTO(Long id, String firstname, String lastname, String phoneNumber,String email, AddressDTO address, LocalDate createdDate, String companyName, String tva, String bankNumber) {
        super(id, firstname, lastname, phoneNumber, email, address, createdDate);
        this.companyName = companyName;
        this.tva = tva;
        this.bankNumber = bankNumber;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
