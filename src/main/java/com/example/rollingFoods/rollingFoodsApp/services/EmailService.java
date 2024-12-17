package com.example.rollingFoods.rollingFoodsApp.services;

import com.example.rollingFoods.rollingFoodsApp.dto.FoodTruckOwnerDTO;
import com.example.rollingFoods.rollingFoodsApp.models.FoodTruckOwner;
import com.example.rollingFoods.rollingFoodsApp.models.UserCredential;

public interface EmailService {



    void sendEmailConfirmation(FoodTruckOwner foodTruckOwnerDTO, UserCredential userCredential);
}
