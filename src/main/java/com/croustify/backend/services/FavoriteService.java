package com.croustify.backend.services;

import com.croustify.backend.dto.FavoriteDTO;
import com.croustify.backend.dto.FoodTruckDTO;
import com.croustify.backend.models.Favorite;

import java.util.List;

public interface FavoriteService {
    FavoriteDTO addFavorite(Long userCredentialId, Long foodTruckId);
    void removeFavorite(Long userCredentialId, Long favoriteId);
    List<FavoriteDTO> getFavorites(Long userCredentialId);
}
