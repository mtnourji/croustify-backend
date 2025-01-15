package com.croustify.backend.repositories;

import com.croustify.backend.models.FoodTruck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodTruckRepo extends JpaRepository<FoodTruck, Long>, JpaSpecificationExecutor<FoodTruck> {
    @Query("SELECT f.id FROM FoodTruck f WHERE f.foodTruckOwner.id = :foodTruckOwnerId")
    Long findByFoodTruckOwnerId(Long foodTruckOwnerId);
    @Query("SELECT f.isOpen FROM FoodTruck f WHERE f.id = :id")
    boolean findStatusById(Long id);
    @Query("SELECT f.foodTruck FROM Favorite f WHERE f.userCredential.id = :userCredentialId")
    List<FoodTruck> findAllMyFavorites(@Param("userCredentialId") Long userCredentialId);

    List<FoodTruck> findByFoodTruckOwnerUserCredentialId(long userId);
}
