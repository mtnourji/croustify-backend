package com.example.rollingFoods.rollingFoodsApp.services.imp;

import com.example.rollingFoods.rollingFoodsApp.enums.FoodType;
import com.example.rollingFoods.rollingFoodsApp.mappers.FoodTruckMapper;
import com.example.rollingFoods.rollingFoodsApp.dto.FoodTruckDTO;
import com.example.rollingFoods.rollingFoodsApp.models.FoodTruck;
import com.example.rollingFoods.rollingFoodsApp.models.FoodTruckOwner;
import com.example.rollingFoods.rollingFoodsApp.repositories.FoodTruckOwnerRepo;
import com.example.rollingFoods.rollingFoodsApp.repositories.FoodTruckRepo;
import com.example.rollingFoods.rollingFoodsApp.repositories.UserCredentialRepo;
import com.example.rollingFoods.rollingFoodsApp.services.TruckService;
import io.jsonwebtoken.io.IOException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TruckServiceImp implements TruckService {

    @Value("D://Projet rollingFoodsApp/pictures/foodTruck")
    private String foodTruckPicturesLocation;

    @Value("http://10.0.2.2:8686/api/images/foodTruck/")
    private String foodTrucksStaticRessiurcesUrl;

    @Autowired
    private UserCredentialRepo userCredentialRepo;

    @Autowired
    private FoodTruckOwnerRepo foodTruckOwnerRepo;

    @Autowired
    private FoodTruckRepo foodTruckRepo;

    @Autowired
    private FoodTruckMapper mapper;

    public static List<FoodTruck> trucks = new ArrayList<>();


    // Get all food trucks
    @Override
    public List<FoodTruckDTO> getTrucks() {
        final List<FoodTruck> trucks = foodTruckRepo.findAll();
        return trucks.stream().map(mapper::foodTruckToDto).collect(Collectors.toList());
    }



    // Create food truck
    @Override
    public FoodTruckDTO createTruck(final FoodTruckDTO foodTruckDTO, final Long foodTruckOwnerId, final MultipartFile file) throws IOException, java.io.IOException {
        final FoodTruckOwner foodTruckOwner = foodTruckOwnerRepo.findById(foodTruckOwnerId).orElseThrow(() -> new EntityNotFoundException("Food truck owner not found with id: " + foodTruckOwnerId));
        FoodTruck foodTruck = new FoodTruck();
        foodTruck.setName(foodTruckDTO.getName());
        foodTruck.setDescription(foodTruckDTO.getDescription());
        foodTruck.setSpeciality(foodTruckDTO.getSpeciality());
        foodTruck.setFoodType(foodTruckDTO.getFoodType());
        foodTruck.setCoordinates(foodTruckDTO.getCoordinates());
        foodTruck.setFoodTruckOwner(foodTruckOwner);

        final FoodTruck savedFoodTruck = foodTruckRepo.save(foodTruck);

        if(file != null && !file.isEmpty()) {
            String imageUrl = uploadProfileImage(file, savedFoodTruck.getId());

            savedFoodTruck.setProfileImage(imageUrl);

            foodTruckRepo.save(savedFoodTruck);



        }

        return mapper.foodTruckToDto(savedFoodTruck);
    }


    //Find truck by id
    @Override
    public FoodTruckDTO findTruckById(Long id) {
        final FoodTruck truck = getTruckById((long) id);
        final FoodTruckDTO truckDTO = mapper.foodTruckToDto(truck);
        return truckDTO;
    }

    // Get food truck by id

    private FoodTruck getTruckById(Long id) {
        return foodTruckRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Truck not found with id: " + id));
    }



    //update truck
    @Override
    public FoodTruckDTO updateTruck(Long id, FoodTruckDTO foodTruckDTO) {
        final FoodTruck truck = getTruckById(id);

        if (foodTruckDTO.getName() != null) {
            truck.setName(foodTruckDTO.getName());
        }
        if (foodTruckDTO.getDescription() != null) {
            truck.setDescription(foodTruckDTO.getDescription());
        }
        if (foodTruckDTO.getSpeciality() != null) {
            truck.setSpeciality(foodTruckDTO.getSpeciality());
        }
        if (foodTruckDTO.getFoodType() != null) {
            truck.setFoodType(foodTruckDTO.getFoodType());
        }
        if (foodTruckDTO.getProfileImage() != null) {
            truck.setProfileImage(foodTruckDTO.getProfileImage());
        }

        final FoodTruck updatedTruck = foodTruckRepo.save(truck);
        return mapper.foodTruckToDto(updatedTruck);
    }

    @Override
    public Page<FoodTruckDTO> getTrucksPageable(Pageable pageable) {
        final Page<FoodTruck> trucks = foodTruckRepo.findAll(pageable);
        return trucks.map(mapper::foodTruckToDto);
    }

    //delete truck
   @Override
    public void deleteTruck(int id) {
        foodTruckRepo.deleteById((long) id);
    }

    //get truck by owner id
    @Override
    public Long getTruckByOwnerId(Long ownerId) {
        return foodTruckRepo.findByFoodTruckOwnerId(ownerId);
    }

    //rate truck
    @Override
    public FoodTruckDTO rateTruck(Long truckId, int rating) {
        final FoodTruck truck = getTruckById(truckId);

        // get the current rating and rating count
        final int currentRating = truck.getRating();
        final int currentRatingCount = truck.getRatingCount();

        // calculate the new rating and rating count
        final int newRatingCount = currentRatingCount + 1;
        // calculate the new rating
        final double newRating = (currentRating * currentRatingCount + rating) / (double) newRatingCount;
        truck.setRatingCount(newRatingCount);
        truck.setRating((int)Math.round(newRating));
        final FoodTruck updatedTruck = foodTruckRepo.save(truck);
        return mapper.foodTruckToDto(updatedTruck);
    }

    //Open truck
    @Override
    public FoodTruckDTO openTruck(Long truckId, FoodTruckDTO foodTruckDTO) {
        final FoodTruck truck = getTruckById(truckId);

        if (foodTruckDTO.getCoordinates() != null) {
            truck.setCoordinates(foodTruckDTO.getCoordinates());
            truck.setOpen(true);
        }
        final FoodTruck updatedTruck = foodTruckRepo.save(truck);
        return mapper.foodTruckToDto(updatedTruck);

    }

    //Close truck
    @Override
    public FoodTruckDTO closeTruck(Long truckId) {
        final FoodTruck truck = getTruckById(truckId);
        truck.setOpen(false);
        final FoodTruck updatedTruck = foodTruckRepo.save(truck);
        return mapper.foodTruckToDto(updatedTruck);
    }

    //find truck is open or not
    @Override
    public boolean findStatusById(Long id) {
        boolean status = foodTruckRepo.findStatusById(id);
        return status;

    }

    //Upload truck profile image
    @Override
    public String uploadProfileImage(MultipartFile file, Long truckId) throws IOException, java.io.IOException {

        FoodTruck truck = foodTruckRepo.findById(truckId).orElseThrow(() -> new EntityNotFoundException("Truck not found with id: " + truckId));

        final Path locationPath = Paths.get(foodTruckPicturesLocation, String.valueOf(truck.getId()));

        if(!Files.exists(locationPath)) {
            Files.createDirectory(locationPath);
        }

        try {
            final Path location = locationPath.resolve(StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), location, StandardCopyOption.REPLACE_EXISTING);
            truck.setProfileImage(foodTrucksStaticRessiurcesUrl + truck.getId() + "/" + StringUtils.cleanPath(file.getOriginalFilename()));
            foodTruckRepo.save(truck);
            return truck.getProfileImage();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Image upload failed");

        }

    }

    //Find truck by food type
    @Override
    public List<FoodTruckDTO> findByFoodType(FoodType foodType) {
        final List<FoodTruck> trucks = foodTruckRepo.findByFoodType(foodType);
        return trucks.stream().map(mapper::foodTruckToDto).collect(Collectors.toList());
    }

    //Find truck by name and food type and description
    @Override
    public List<FoodTruckDTO> findByNameAndFoodTypeAndDescription(String searchTerm) {
        final List<FoodTruck> trucks = foodTruckRepo.findByNameAndFoodTypeAndDescription(searchTerm);
        return trucks.stream().map(mapper::foodTruckToDto).collect(Collectors.toList());
    }
}














