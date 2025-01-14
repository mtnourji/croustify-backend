package com.croustify.backend.repositories;

import com.croustify.backend.enums.FoodType;
import com.croustify.backend.models.FoodTruck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface FoodTruckRepo extends JpaRepository<FoodTruck, Long> {
    //Find food truck by  food truck owner id
    @Query("SELECT f.id FROM FoodTruck f WHERE f.foodTruckOwner.id = :foodTruckOwnerId")
    Long findByFoodTruckOwnerId(Long foodTruckOwnerId);
    //Find food truck status by id
    @Query("SELECT f.isOpen FROM FoodTruck f WHERE f.id = :id")
    boolean findStatusById(Long id);
    //Find foodTtype
    @Query("SELECT f FROM FoodTruck f where :foodType MEMBER OF f.foodType")
    List<FoodTruck> findByFoodType (@Param("foodType") FoodType foodType);
    //Find food truck by name and food type and description
    @Query("SELECT f from FoodTruck  f WHERE LOWER (f.name) LIKE LOWER(CONCAT('%', :searchTerm, '%') ) OR LOWER(f.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<FoodTruck> findByNameAndFoodTypeAndDescription(@Param("searchTerm") String searchTerm);

    @Query("SELECT f.foodTruck FROM Favorite f WHERE f.userCredential.id = :userCredentialId")
    List<FoodTruck> findAllMyFavorites(@Param("userCredentialId") Long userCredentialId);
}
