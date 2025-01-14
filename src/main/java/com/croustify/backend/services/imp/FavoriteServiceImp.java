package com.croustify.backend.services.imp;

import com.croustify.backend.dto.FavoriteDTO;
import com.croustify.backend.dto.FoodTruckDTO;
import com.croustify.backend.mappers.FavoriteMapper;
import com.croustify.backend.mappers.FoodTruckMapper;
import com.croustify.backend.models.Favorite;
import com.croustify.backend.models.FoodTruck;
import com.croustify.backend.models.UserCredential;
import com.croustify.backend.repositories.FavoriteRepo;
import com.croustify.backend.repositories.FoodTruckRepo;
import com.croustify.backend.repositories.UserCredentialRepo;
import com.croustify.backend.services.FavoriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImp implements FavoriteService {

    private final static Logger logger = LoggerFactory.getLogger(FavoriteServiceImp.class.getName());

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
    @Transactional
    public FavoriteDTO addFavorite(Long userCredentialId, Long foodTruckId) {
        logger.info("Adding favorite for userCredentialId: " + userCredentialId + " and foodTruckId: " + foodTruckId);
        FoodTruck truck = foodTruckRepo.getReferenceById(foodTruckId);
        UserCredential user = userCredentialRepo.getReferenceById(userCredentialId);
        final Optional<Favorite> existingFavorite = Optional.ofNullable(favoriteRepo.findByUserCredentialIdAndFoodTruckId(userCredentialId, foodTruckId));
        if (existingFavorite.isPresent()) {
            logger.info("Favorite {} {} already exists", userCredentialId, foodTruckId);
            new FavoriteDTO(existingFavorite.get().getId(), userCredentialId, foodTruckId);
        }
        Favorite favorite = new Favorite();
        favorite.setFoodTruck(truck);
        favorite.setUserCredential(user);
        final Favorite saved = favoriteRepo.save(favorite);
        return new FavoriteDTO(saved.getId(), userCredentialId, foodTruckId);
    }

    @Override
    public void removeFavorite(Long userCredentialId, Long favoriteId) {
        logger.info("Removing favorite for userCredentialId: " + userCredentialId + " and favorite: " + favoriteId);
        Favorite favorite = favoriteRepo.getReferenceByUserCredentialIdAndId(userCredentialId, favoriteId);
        if (favorite != null) {
            favoriteRepo.delete(favorite);
        }
    }

    @Override
    @Transactional
    public List<FavoriteDTO> getFavorites(Long userCredentialId) {
        logger.info("Getting favorites for userCredentialId: " + userCredentialId);
        List<Favorite> favorites = favoriteRepo.findByUserCredentialId(userCredentialId);
        return favorites.stream()
                .map(favorite -> new FavoriteDTO(favorite.getId(), userCredentialId, favorite.getFoodTruck().getId()))
                .collect(Collectors.toList());
    }

}
