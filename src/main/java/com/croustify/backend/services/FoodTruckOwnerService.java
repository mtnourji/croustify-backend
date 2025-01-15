package com.croustify.backend.services;

import com.croustify.backend.dto.FoodTruckOwnerDTO;
import com.croustify.backend.dto.NewFoodTruckOwnerDTO;
import com.croustify.backend.dto.UserCredentialDTO;

public interface FoodTruckOwnerService {

    void createFoodTruckOwner(NewFoodTruckOwnerDTO foodTruckOwnerRequest);

    // add a food truck owner to the database
    FoodTruckOwnerDTO addFoodTruckOwner(Long userCredentialId, FoodTruckOwnerDTO foodTruckOwnerDTO);

    //get FoodTruckOwnerId by userCredentialId
    Long findFoodTruckOwnerIdByUserCredentialId(Long userCredentialId);

    Boolean isFoodTruckOwner(Long userCredentialId);
}
