package com.croustify.backend.services;

import com.croustify.backend.dto.NewFoodTruckOwnerDTO;

public interface FoodTruckOwnerService {

    void createFoodTruckOwner(NewFoodTruckOwnerDTO foodTruckOwnerRequest);

    Long findFoodTruckOwnerIdByUserCredentialId(Long userCredentialId);

    Boolean isFoodTruckOwner(Long userCredentialId);
}
