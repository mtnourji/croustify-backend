package com.croustify.backend.services;

import com.croustify.backend.dto.ContactDTO;
import com.croustify.backend.models.FoodTruckOwner;
import com.croustify.backend.models.UserCredential;

public interface EmailService {



    void sendEmailConfirmation(FoodTruckOwner foodTruckOwnerDTO, UserCredential userCredential);

    void sendEmailNotificationContact(ContactDTO contactDTO);
}
