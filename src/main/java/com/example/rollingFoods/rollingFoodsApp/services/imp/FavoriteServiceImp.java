package com.example.rollingFoods.rollingFoodsApp.services.imp;

import com.example.rollingFoods.rollingFoodsApp.dto.FavoriteDTO;
import com.example.rollingFoods.rollingFoodsApp.dto.FoodTruckDTO;
import com.example.rollingFoods.rollingFoodsApp.mappers.FavoriteMapper;
import com.example.rollingFoods.rollingFoodsApp.mappers.FoodTruckMapper;
import com.example.rollingFoods.rollingFoodsApp.models.Favorite;
import com.example.rollingFoods.rollingFoodsApp.models.FoodTruck;
import com.example.rollingFoods.rollingFoodsApp.models.UserCredential;
import com.example.rollingFoods.rollingFoodsApp.repositories.FavoriteRepo;
import com.example.rollingFoods.rollingFoodsApp.repositories.FoodTruckRepo;
import com.example.rollingFoods.rollingFoodsApp.repositories.UserCredentialRepo;
import com.example.rollingFoods.rollingFoodsApp.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImp implements FavoriteService {

    Logger logger = Logger.getLogger(FavoriteServiceImp.class.getName());

    @Autowired
    private FavoriteRepo favoriteRepo;

    @Autowired
    private UserCredentialRepo userCredentialRepo;

    @Autowired
    private FoodTruckRepo foodTruckRepo;

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private FoodTruckMapper foodTruckMapper;

    @Override
    public Favorite addFavorite(Long userCredentialId, Long foodTruckId) {
        logger.info("Adding favorite for userCredentialId: " + userCredentialId + " and foodTruckId: " + foodTruckId);
        FoodTruck truck = foodTruckRepo.findById(foodTruckId).orElse(null);
        UserCredential user = userCredentialRepo.findById(userCredentialId).orElse(null);
        if (truck != null && user != null) {
            //Verify if the favorite already exists
            Optional<Favorite> favoriteOptional = Optional.ofNullable(favoriteRepo.findByUserCredentialAndFoodTruck(user, truck));
            if (favoriteOptional.isPresent()) {
                logger.info("Favorite already exists");
                return favoriteOptional.get();
            }
            //Create the favorite
            Favorite favorite = new Favorite();
            favorite.setFoodTruck(truck);
            favorite.setUserCredential(user);
            favoriteRepo.save(favorite);

            return favorite;
        }

        return null;
    }

    @Override
    public void removeFavorite(Long userCredentialId, Long foodTruckId) {
        logger.info("Removing favorite for userCredentialId: " + userCredentialId + " and foodTruckId: " + foodTruckId);
        UserCredential user = userCredentialRepo.findById(userCredentialId).orElse(null);
        FoodTruck truck = foodTruckRepo.findById(foodTruckId).orElse(null);
        if (user != null && truck != null) {
            Favorite favorite = favoriteRepo.findByUserCredentialAndFoodTruck(user, truck);
            if (favorite != null) {
                favoriteRepo.delete(favorite);
            }
        }
    }

    @Override
    public boolean isFavorite(Long userCredentialId, Long foodTruckId) {
        logger.info("Checking if favorite for userCredentialId: " + userCredentialId + " and foodTruckId: " + foodTruckId);
        UserCredential user = userCredentialRepo.findById(userCredentialId).orElse(null);
        FoodTruck truck = foodTruckRepo.findById(foodTruckId).orElse(null);
        if (user != null && truck != null) {
            Favorite favorite = favoriteRepo.findByUserCredentialAndFoodTruck(user, truck);
            return favorite != null;
        }
        return false;
    }

    @Override
    public List<FoodTruckDTO> getFavorites(Long userCredentialId) {
        logger.info("Getting favorites for userCredentialId: " + userCredentialId);

        // Récupérer l'utilisateur
        UserCredential user = userCredentialRepo.findById(userCredentialId).orElse(null);

        if (user != null) {
            // Récupérer tous les food trucks favoris de l'utilisateur
            List<Favorite> favorites = favoriteRepo.findByUserCredential(user);

            // Extraire les food trucks des favoris
            List<FoodTruck> foodTrucks = favorites.stream()
                    .map(Favorite::getFoodTruck)
                    .collect(Collectors.toList());

            // Transformer les food trucks en DTO et retourner la liste
            return foodTrucks.stream()
                    .map(foodTruckMapper::foodTruckToDto)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList(); // Si aucun favori, renvoyer une liste vide
    }


}
