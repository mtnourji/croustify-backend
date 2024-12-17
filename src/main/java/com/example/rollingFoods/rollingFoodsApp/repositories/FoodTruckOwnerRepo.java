package com.example.rollingFoods.rollingFoodsApp.repositories;

import com.example.rollingFoods.rollingFoodsApp.models.FoodTruckOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FoodTruckOwnerRepo extends JpaRepository<FoodTruckOwner, Long> {

@Query("SELECT f.id FROM FoodTruckOwner f WHERE f.userCredential.id = :userCredentialId")
    Long findFoodTruckOwnerIdByUserCredentialId(Long userCredentialId);

    Boolean existsByUserCredentialId(Long userCredentialId);
}
