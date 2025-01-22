package com.croustify.backend.services;

import com.croustify.backend.dto.FoodTruckOwnerDTO;
import com.croustify.backend.dto.NewFoodTruckOwnerDTO;
import com.croustify.backend.dto.UserCredentialDTO;

public interface FoodTruckOwnerService {

    void createFoodTruckOwner(NewFoodTruckOwnerDTO foodTruckOwnerRequest);

    //get FoodTruckOwnerId by userCredentialId
    Long findFoodTruckOwnerIdByUserCredentialId(Long userCredentialId);

    Boolean isFoodTruckOwner(Long userCredentialId);
}
