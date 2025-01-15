package com.croustify.backend.services;

import com.croustify.backend.dto.FoodTruckDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TruckService {

    List<FoodTruckDTO> getTrucks(boolean onlyFavorites);

    void deleteTruck(int id);

    FoodTruckDTO createTruck(FoodTruckDTO foodTruckDTO, Long foodTruckOwnerId, MultipartFile file) throws IOException, java.io.IOException;

    FoodTruckDTO findTruckById(Long id);

    List<FoodTruckDTO> searchFoodTrucks(Boolean isOpen, List<Long> categoryIds, Boolean onlyFavorites);

    public FoodTruckDTO updateTruck(Long id, FoodTruckDTO foodTruckDTO);
    public Page<FoodTruckDTO> getTrucksPageable(Pageable pageable);
    public Long getTruckByOwnerId(Long ownerId);
    public FoodTruckDTO rateTruck(Long truckId, int rating);
    public FoodTruckDTO openTruck(Long truckId, FoodTruckDTO foodTruckDTO);
    public FoodTruckDTO closeTruck(Long truckId);
    public boolean findStatusById(Long id);
    public String uploadProfileImage(MultipartFile file, Long truckId) throws IOException;
}
