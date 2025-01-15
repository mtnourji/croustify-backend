package com.croustify.backend.dto;

public class NewFoodTruckOwnerDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String companyName;
    private AddressDTO address;
    private int numberOfAllowedFoodTrucks;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public int getNumberOfAllowedFoodTrucks() {
        return numberOfAllowedFoodTrucks;
    }

    public void setNumberOfAllowedFoodTrucks(int numberOfAllowedFoodTrucks) {
        this.numberOfAllowedFoodTrucks = numberOfAllowedFoodTrucks;
    }
}
