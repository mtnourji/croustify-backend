package com.croustify.backend.controllers;

import com.croustify.backend.dto.FavoriteDTO;
import com.croustify.backend.dto.FoodTruckDTO;
import com.croustify.backend.models.Favorite;
import com.croustify.backend.services.FavoriteService;
import com.croustify.backend.validation.OwnUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8686")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;


    @OwnUser(paramName = "id")
    @GetMapping("/users/{id}/favorites")
    ResponseEntity<List<FavoriteDTO>> getFavorites(@PathVariable(value = "id") Long userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(favoriteService.getFavorites(userId));
        }
    }

    @OwnUser(paramName = "id")
    @PostMapping("/users/{id}/favorites")
    ResponseEntity<FavoriteDTO> addFavorite(@PathVariable(value = "id") Long userId, @RequestParam Long foodTruckId) {
        if (userId == null || foodTruckId == null) {
            return ResponseEntity.badRequest().build();
        }
        FavoriteDTO favorite = favoriteService.addFavorite(userId, foodTruckId);
        return ResponseEntity.ok(favorite);
    }

    @OwnUser
    @DeleteMapping("/users/{userId}/favorites/{favoriteId}")
    ResponseEntity<Void> removeFavorite(@PathVariable(value = "userId") Long userId, @PathVariable(value = "favoriteId") Long favoriteId) {
        favoriteService.removeFavorite(userId, favoriteId);
        return ResponseEntity.ok().build();
    }

}
