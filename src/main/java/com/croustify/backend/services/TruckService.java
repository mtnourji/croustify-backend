package com.croustify.backend.services;

import com.croustify.backend.dto.FoodTruckDTO;
import com.croustify.backend.dto.OpenFoodTruckRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TruckService {

    List<FoodTruckDTO> getTrucks(boolean onlyFavorites);

    void deleteTruck(int id);

    FoodTruckDTO createTruck(FoodTruckDTO foodTruckDTO, Long foodTruckOwnerId, MultipartFile file) throws IOException, java.io.IOException;


    List<FoodTruckDTO> searchFoodTrucks(Boolean isOpen, List<Long> categoryIds, Boolean onlyFavorites, Double lat, Double lng, Integer radiusInKm);

    FoodTruckDTO updateTruck(Long id, FoodTruckDTO foodTruckDTO);
    FoodTruckDTO rateTruck(Long truckId, int rating);
    void openTruck(Long truckId, OpenFoodTruckRequestDTO foodTruckDTO);
    void closeTruck(Long truckId);
    boolean findStatusById(Long id);
    String uploadProfileImage(MultipartFile file, Long truckId) throws IOException;

    List<FoodTruckDTO> getOwnerTrucks(long userId);

    FoodTruckDTO getOwnerTruck(long userId, long foodTruckId);

}
