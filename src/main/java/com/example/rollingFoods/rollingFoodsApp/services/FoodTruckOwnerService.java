package com.example.rollingFoods.rollingFoodsApp.services;

import com.example.rollingFoods.rollingFoodsApp.dto.FoodTruckOwnerDTO;
import com.example.rollingFoods.rollingFoodsApp.dto.UserCredentialDTO;

public interface FoodTruckOwnerService {

    // Registering the FoodTruckOwner
    UserCredentialDTO registerFoodTruckUser(UserCredentialDTO userCredentialDTO);

    // add a food truck owner to the database
    FoodTruckOwnerDTO addFoodTruckOwner(Long userCredentialId, FoodTruckOwnerDTO foodTruckOwnerDTO);

    //get FoodTruckOwnerId by userCredentialId
    Long findFoodTruckOwnerIdByUserCredentialId(Long userCredentialId);

    Boolean isFoodTruckOwner(Long userCredentialId);
}
