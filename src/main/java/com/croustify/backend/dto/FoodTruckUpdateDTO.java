package com.croustify.backend.dto;

import java.util.List;

public class FoodTruckUpdateDTO {

    private String name;
    private String description;
    private String speciality;
    private String profileImage;

    private AddressDTO defaultAddress;
    private List<CategoryDTO> categories;

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

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public AddressDTO getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(AddressDTO defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}

