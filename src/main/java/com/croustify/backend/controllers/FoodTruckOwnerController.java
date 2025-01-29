package com.croustify.backend.controllers;

import com.croustify.backend.repositories.UserCredentialRepo;
import com.croustify.backend.dto.NewFoodTruckOwnerDTO;
import com.croustify.backend.services.EmailService;
import com.croustify.backend.services.FoodTruckOwnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8686")
@RestController
public class FoodTruckOwnerController {
    private static final Logger logger = LoggerFactory.getLogger(FoodTruckOwnerController.class);

    @Autowired
    private FoodTruckOwnerService foodTruckOwnerService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserCredentialRepo userCredentialRepo;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/foodTruckOwners")
     ResponseEntity<Void> registerFoodTruckOwner(@RequestBody @Validated NewFoodTruckOwnerDTO owner) {
        logger.info("Registering FoodTruckOwner: {} {}", owner.getEmail(), owner.getCompanyName());
        foodTruckOwnerService.createFoodTruckOwner(owner);
        // TODO at least return id
        return ResponseEntity.noContent().build();
    }

    //get FoodTruckOwnerId by userCredentialId
    @GetMapping("/findFoodTruckOwnerIdByUserCredentialId")
    public ResponseEntity<Long> findFoodTruckOwnerIdByUserCredentialId(@RequestParam Long userCredentialId) {
        logger.info("Finding FoodTruckOwnerId by userCredentialId: {}", userCredentialId);
        Long findFoodTruckOwnerIdByUserCredentialId = foodTruckOwnerService.findFoodTruckOwnerIdByUserCredentialId(userCredentialId);
        return ResponseEntity.ok(findFoodTruckOwnerIdByUserCredentialId);
    }

    @GetMapping("/isFoodTruckOwner")
    public ResponseEntity<Boolean> isFoodTruckOwner(@RequestParam Long userCredentialId) {
        logger.info("Checking if the user is a food truck owner: {}" , userCredentialId);
        Boolean isFoodTruckOwner = foodTruckOwnerService.isFoodTruckOwner(userCredentialId);
        logger.info("Is the user a food truck owner: {}" , isFoodTruckOwner);
        return ResponseEntity.ok(isFoodTruckOwner);
    }


}
