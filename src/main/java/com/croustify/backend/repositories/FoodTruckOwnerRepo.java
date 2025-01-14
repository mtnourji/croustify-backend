package com.croustify.backend.repositories;

import com.croustify.backend.models.FoodTruckOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FoodTruckOwnerRepo extends JpaRepository<FoodTruckOwner, Long> {

    @Query("SELECT f.id FROM FoodTruckOwner f WHERE f.userCredential.id = :userCredentialId")
    Long findFoodTruckOwnerIdByUserCredentialId(Long userCredentialId);

    Optional<FoodTruckOwner> getReferenceByUserCredentialId(Long userCredentialId);

    Boolean existsByUserCredentialId(Long userCredentialId);
}
