package com.example.rollingFoods.rollingFoodsApp.repositories;

import com.example.rollingFoods.rollingFoodsApp.models.Favorite;
import com.example.rollingFoods.rollingFoodsApp.models.FoodTruck;
import com.example.rollingFoods.rollingFoodsApp.models.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepo extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserCredential(UserCredential userCredential);
    Favorite findByUserCredentialAndFoodTruck(UserCredential userCredential, FoodTruck foodTruck);
}
