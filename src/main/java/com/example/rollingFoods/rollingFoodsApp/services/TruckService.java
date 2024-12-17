package com.example.rollingFoods.rollingFoodsApp.services;

import com.example.rollingFoods.rollingFoodsApp.dto.FoodTruckDTO;
import com.example.rollingFoods.rollingFoodsApp.enums.FoodType;
import com.example.rollingFoods.rollingFoodsApp.models.FoodTruck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

public interface TruckService {

    //Get all trucks
    public List<FoodTruckDTO> getTrucks();

    //Delete truck
    public void deleteTruck(int id);

    // Create food truck
    FoodTruckDTO createTruck(FoodTruckDTO foodTruckDTO, Long foodTruckOwnerId, MultipartFile file) throws IOException, java.io.IOException;

    //Find truck by id
    FoodTruckDTO findTruckById(Long id);
    //Update truck
    public FoodTruckDTO updateTruck(Long id, FoodTruckDTO foodTruckDTO);
    //Get all trucks with pagination
    public Page<FoodTruckDTO> getTrucksPageable(Pageable pageable);
    //Get truck by owner id
    public Long getTruckByOwnerId(Long ownerId);
    //Truck rating
    public FoodTruckDTO rateTruck(Long truckId, int rating);
    //Open truck
    public FoodTruckDTO openTruck(Long truckId, FoodTruckDTO foodTruckDTO);
    //Close truck
    public FoodTruckDTO closeTruck(Long truckId);
    //find truck is open or not
    public boolean findStatusById(Long id);
    //Uplaod truck profile image
    public String uploadProfileImage(MultipartFile file, Long truckId) throws IOException;
    //Find truck by food type
    public List<FoodTruckDTO> findByFoodType(FoodType foodType);
    //Find truck by name and food type and description
    public List<FoodTruckDTO> findByNameAndFoodTypeAndDescription(String searchTerm);





}
