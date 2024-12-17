package com.example.rollingFoods.rollingFoodsApp.controllers;

import com.example.rollingFoods.rollingFoodsApp.dto.FoodTruckDTO;
import com.example.rollingFoods.rollingFoodsApp.models.Favorite;
import com.example.rollingFoods.rollingFoodsApp.services.FavoriteService;
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

    @GetMapping("/favorite")
    ResponseEntity <List<FoodTruckDTO>>getFavorite(@RequestParam Long userId){ {
        if(userId == null){
            return ResponseEntity.badRequest().build();
        }else {
            return ResponseEntity.ok(favoriteService.getFavorites(userId));
            }
        }
    }

    @PostMapping("/favorite")
    ResponseEntity <Favorite> addFavorite(@RequestParam Long userId,@RequestParam Long foodTruckId){
        if(userId == null || foodTruckId == null){
            return ResponseEntity.badRequest().build();
        }
        Favorite favorite = favoriteService.addFavorite(userId, foodTruckId);
        if(favorite == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }else {
            return ResponseEntity.ok(favorite);
        }
    }

    @GetMapping("/isFavorite")
    ResponseEntity <Boolean> isFavorite(@RequestParam Long userId,@RequestParam Long foodTruckId){
        if(userId == null || foodTruckId == null){
            return ResponseEntity.badRequest().build();
        }else {
            return ResponseEntity.ok(favoriteService.isFavorite(userId, foodTruckId));
        }
    }

    // Remove favorite
    @DeleteMapping("/favorite")
    ResponseEntity <Void> removeFavorite(@RequestParam Long userId,@RequestParam Long foodTruckId){
        if(userId == null || foodTruckId == null){
            return ResponseEntity.badRequest().build();
        }else {
            favoriteService.removeFavorite(userId, foodTruckId);
            return ResponseEntity.ok().build();
        }
    }

}
