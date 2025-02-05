package com.croustify.backend.services.imp;

import com.croustify.backend.dto.CategoryDTO;
import com.croustify.backend.dto.FoodTruckDTO;
import com.croustify.backend.dto.OpenFoodTruckMode;
import com.croustify.backend.dto.OpenFoodTruckRequestDTO;
import com.croustify.backend.mappers.FoodTruckMapper;
import com.croustify.backend.models.FoodTruck;
import com.croustify.backend.models.FoodTruckOwner;
import com.croustify.backend.models.embedded.Coordinates;
import com.croustify.backend.repositories.CategoryRepository;
import com.croustify.backend.repositories.FoodTruckOwnerRepo;
import com.croustify.backend.repositories.FoodTruckRepo;
import com.croustify.backend.repositories.UserCredentialRepo;
import com.croustify.backend.services.TruckService;
import com.croustify.backend.specifications.FoodTruckSpecifications;
import com.croustify.backend.util.SecurityUtil;
import io.jsonwebtoken.io.IOException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TruckServiceImp implements TruckService {

    private static final int DEFAULT_RADIUS_IN_KM = 10;
    @Value("${storage.trucks-image-location}")
    private String foodTruckPicturesLocation;

    @Value("http://10.0.2.2:8686/api/images/foodTruck/")
    private String foodTrucksStaticRessiurcesUrl;

    @Autowired
    private UserCredentialRepo userCredentialRepo;

    @Autowired
    private FoodTruckOwnerRepo foodTruckOwnerRepo;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FoodTruckRepo foodTruckRepo;

    @Autowired
    private FoodTruckMapper mapper;

    @Override
    @Transactional
    public List<FoodTruckDTO> getTrucks(boolean onlyFavorites) {
        final List<FoodTruck> trucks;
        if(onlyFavorites){
            trucks = foodTruckRepo.findAllMyFavorites(SecurityUtil.getConnectedUserOrThrow().getId());
        } else {
            trucks = foodTruckRepo.findAll();
        }
        return trucks.stream().map(mapper::foodTruckToDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<FoodTruckDTO> getOwnerTrucks(long userId) {
        final List<FoodTruck> foodTrucks = foodTruckRepo.findByFoodTruckOwnerUserCredentialId(userId);
        return foodTrucks.stream().map(mapper::foodTruckToDto).collect(Collectors.toList());
    }

    @Override
    public FoodTruckDTO createTruck(final FoodTruckDTO foodTruckDTO, final Long userId, final MultipartFile file) throws IOException, java.io.IOException {
        final FoodTruckOwner foodTruckOwner = foodTruckOwnerRepo.getReferenceByUserCredentialId(userId).orElseThrow(() -> new EntityNotFoundException("Food truck owner not found with user id: " + userId));

        if(foodTruckOwner.getFoodTrucks().size() >= foodTruckOwner.getNumberOfAllowedFoodTrucks()){
            throw new IllegalArgumentException("Number of maximum food trucks reached for user " + userId);
        }

        FoodTruck foodTruck = new FoodTruck();
        foodTruck.setName(foodTruckDTO.getName());
        foodTruck.setDescription(foodTruckDTO.getDescription());
        foodTruck.setSpeciality(foodTruckDTO.getSpeciality());
        if(!foodTruckDTO.getCategories().isEmpty()) {
            foodTruck.setCategories(new HashSet<>(categoryRepository.findAllById(foodTruckDTO.getCategories().stream().map(CategoryDTO::getId).toList())));
        }
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

    @Override
    @Transactional
    public FoodTruckDTO getOwnerTruck(long userId, long foodTruckId) {
        final FoodTruck truck = getTruckById(foodTruckId);
        return mapper.foodTruckToDto(truck);
    }

    private FoodTruck getTruckById(Long id) {
        return foodTruckRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Truck not found with id: " + id));
    }

    @Transactional
    @Override
    public List<FoodTruckDTO> searchFoodTrucks(Boolean isOpen, List<Long> categoryIds, Boolean onlyFavorites, Double lat, Double lng, Integer radiusInKm) {
        Specification<FoodTruck> spec = Specification.where(null);
        if (isOpen != null) {
            spec = spec.and(FoodTruckSpecifications.isOpen(isOpen));
        }
        if (categoryIds != null && !categoryIds.isEmpty()) {
            spec = spec.and(FoodTruckSpecifications.hasCategories(categoryIds));
        }
        if (Boolean.TRUE.equals(onlyFavorites)) {
            Long userCredentialId = SecurityUtil.getConnectedUserOrThrow().getId();
            spec = spec.and(FoodTruckSpecifications.isFavoriteForUser(userCredentialId));
        }

        final List<FoodTruck> all = foodTruckRepo.findAll(spec);
        if(lat != null){
            final List<FoodTruck> byLocationWithinRadius = foodTruckRepo.findByLocationWithinRadius(lat, lng, Optional.ofNullable(radiusInKm).orElse(DEFAULT_RADIUS_IN_KM), all.stream().map(FoodTruck::getId).toList());
            return mapper.foodTruckToDto(byLocationWithinRadius);
        }

        return mapper.foodTruckToDto(all);
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
        truck.getCategories().clear();
        if(!foodTruckDTO.getCategories().isEmpty()){
            truck.setCategories(new HashSet<>(categoryRepository.findAllById(foodTruckDTO.getCategories().stream().map(CategoryDTO::getId).toList())));
        }
        if (foodTruckDTO.getProfileImage() != null) {
            truck.setProfileImage(foodTruckDTO.getProfileImage());
        }

        final FoodTruck updatedTruck = foodTruckRepo.save(truck);
        return mapper.foodTruckToDto(updatedTruck);
    }

    @Override
    public void deleteTruck(int id) {
        foodTruckRepo.deleteById((long) id);
    }

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

    @Override
    public void openTruck(Long truckId, OpenFoodTruckRequestDTO foodTruckDTO) {
        final FoodTruck truck = getTruckById(truckId);
        final Coordinates coordinates = new Coordinates();
        if(OpenFoodTruckMode.CURRENT_POSITION.equals(foodTruckDTO.getOpenFoodTruckMode())){
            coordinates.setLatitude(foodTruckDTO.getLatLng().getLat());
            coordinates.setLongitude(foodTruckDTO.getLatLng().getLng());
        } else if(OpenFoodTruckMode.FOODTRUCK_ADDRESS.equals(foodTruckDTO.getOpenFoodTruckMode())){
            coordinates.setLongitude(truck.getDefaultCoordinates().getLongitude());
            coordinates.setLatitude(truck.getDefaultCoordinates().getLatitude());
        }
        truck.setCoordinates(coordinates);
        truck.setOpen(true);
        foodTruckRepo.save(truck);
    }

    //Close truck
    @Override
    public void closeTruck(Long truckId) {
        final FoodTruck truck = getTruckById(truckId);
        truck.setOpen(false);
        foodTruckRepo.save(truck);
    }

    //find truck is open or not
    @Override
    public boolean findStatusById(Long id) {
        return foodTruckRepo.findStatusById(id);

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
}














