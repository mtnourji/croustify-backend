package com.croustify.backend.controllers;

import com.croustify.backend.dto.FoodTruckOwnerDTO;
import com.croustify.backend.dto.UserCredentialDTO;
import com.croustify.backend.repositories.UserCredentialRepo;
import com.croustify.backend.models.UserCredential;
import com.croustify.backend.services.EmailService;
import com.croustify.backend.services.FoodTruckOwnerService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8686")
@RestController
public class FoodTruckOwnerController {
    private static final Logger logger = LoggerFactory.getLogger(FoodTruckOwnerController.class);

    // Injecting the FoodTruckOwnerService
    @Autowired
    private FoodTruckOwnerService foodTruckOwnerService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserCredentialRepo userCredentialRepo;

    // Registering the FoodTruckOwner
    @PostMapping("/registerFoodTruckOwner")
     ResponseEntity<UserCredentialDTO> registerFoodTruckOwner(@RequestBody @Validated UserCredentialDTO UserCredentialDTO) {
        logger.info("Registering FoodTruckOwner: {}", UserCredentialDTO);
        UserCredentialDTO registerFoodTruckOwner = foodTruckOwnerService.registerFoodTruckUser(UserCredentialDTO);
        return ResponseEntity.ok(registerFoodTruckOwner);
    }

    // add a food truck owner to the database
    @PostMapping("/addFoodTruckOwner")
    public ResponseEntity<FoodTruckOwnerDTO> addFoodTruckOwner(@RequestParam Long userCredentialId, @RequestBody FoodTruckOwnerDTO foodTruckOwnerDTO) throws MessagingException {
        UserCredential userCredential =  userCredentialRepo.findById(userCredentialId).orElseThrow(() -> new RuntimeException("User not found"));
        logger.info("Adding FoodTruckOwner: {}", foodTruckOwnerDTO);
        FoodTruckOwnerDTO addFoodTruckOwner = foodTruckOwnerService.addFoodTruckOwner(userCredentialId, foodTruckOwnerDTO);
        return ResponseEntity.ok(addFoodTruckOwner);
    }

    //get FoodTruckOwnerId by userCredentialId
    @GetMapping("/findFoodTruckOwnerIdByUserCredentialId")
    public ResponseEntity<Long> findFoodTruckOwnerIdByUserCredentialId(@RequestParam Long userCredentialId) {
        logger.info("Finding FoodTruckOwnerId by userCredentialId: {}", userCredentialId);
        Long findFoodTruckOwnerIdByUserCredentialId = foodTruckOwnerService.findFoodTruckOwnerIdByUserCredentialId(userCredentialId);
        return ResponseEntity.ok(findFoodTruckOwnerIdByUserCredentialId);
    }

    // Check if the user is a food truck owner
    @GetMapping("/isFoodTruckOwner")
    public ResponseEntity<Boolean> isFoodTruckOwner(@RequestParam Long userCredentialId) {
        logger.info("Checking if the user is a food truck owner: {}" , userCredentialId);
        Boolean isFoodTruckOwner = foodTruckOwnerService.isFoodTruckOwner(userCredentialId);
        logger.info("Is the user a food truck owner: {}" , isFoodTruckOwner);
        return ResponseEntity.ok(isFoodTruckOwner);
    }


}
