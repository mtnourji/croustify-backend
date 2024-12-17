package com.example.rollingFoods.rollingFoodsApp.services;

import com.example.rollingFoods.rollingFoodsApp.dto.FavoriteDTO;
import com.example.rollingFoods.rollingFoodsApp.dto.FoodTruckDTO;
import com.example.rollingFoods.rollingFoodsApp.models.Favorite;

import java.util.List;

public interface FavoriteService {
    Favorite addFavorite(Long userCredentialId, Long foodTruckId);
    void removeFavorite(Long userCredentialId, Long foodTruckId);
    boolean isFavorite(Long userCredentialId, Long foodTruckId);
    List<FoodTruckDTO> getFavorites(Long userCredentialId);
}
