package com.croustify.backend.services.imp;

import com.croustify.backend.connector.OpenStreetMapConnector;
import com.croustify.backend.dto.*;
import com.croustify.backend.mappers.AddressMapper;
import com.croustify.backend.mappers.FoodTruckMapper;
import com.croustify.backend.models.FoodTruck;
import com.croustify.backend.models.FoodTruckOwner;
import com.croustify.backend.models.embedded.Address;
import com.croustify.backend.models.embedded.Coordinates;
import com.croustify.backend.repositories.CategoryRepository;
import com.croustify.backend.repositories.FoodTruckOwnerRepo;
import com.croustify.backend.repositories.FoodTruckRepo;
import com.croustify.backend.repositories.UserCredentialRepo;
import com.croustify.backend.services.TruckService;
import com.croustify.backend.specifications.FoodTruckSpecifications;
import com.croustify.backend.util.SecurityUtil;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TruckServiceImp implements TruckService {

    private static final Logger logger = LoggerFactory.getLogger(TruckServiceImp.class);
    private static final int DEFAULT_RADIUS_IN_KM = 10;
    @Value("${storage.trucks-image-location}")
    private String foodTruckPicturesLocation;
    @Value("${backend-image-base-url}")
    private String backendImageBaseUrl;

    @Autowired
    private UserCredentialRepo userCredentialRepo;

    @Autowired
    private OpenStreetMapConnector openStreetMapConnector;

    @Autowired
    private FoodTruckOwnerRepo foodTruckOwnerRepo;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FoodTruckRepo foodTruckRepo;

    @Autowired
    private FoodTruckMapper mapper;
    @Autowired
    private AddressMapper addressMapper;

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

    @Transactional
    @Override
    public FoodTruckDTO createTruck(final FoodTruckDTO foodTruckDTO, final Long userId, final MultipartFile file) throws IOException {
        final FoodTruckOwner foodTruckOwner = foodTruckOwnerRepo.getReferenceByUserCredentialId(userId).orElseThrow(() -> new EntityNotFoundException("Food truck owner not found with user id: " + userId));

        if(foodTruckOwner.getFoodTrucks().size() >= foodTruckOwner.getNumberOfAllowedFoodTrucks()){
            throw new IllegalArgumentException("Number of maximum food trucks reached for user " + userId);
        }

        FoodTruck foodTruck = mapper.dtoToFoodTruck(foodTruckDTO);
        if(!foodTruckDTO.getCategories().isEmpty()) {
            foodTruck.setCategories(new HashSet<>(categoryRepository.findAllById(foodTruckDTO.getCategories().stream().map(CategoryDTO::getId).toList())));
        }
        foodTruck.setCoordinates(foodTruckDTO.getCoordinates());
        if(foodTruck.getDefaultAddress() != null && (foodTruckDTO.getCoordinates() == null || foodTruckDTO.getCoordinates().getLatitude() == null || foodTruckDTO.getCoordinates().getLongitude() == null)){
            final LatLon latLon = openStreetMapConnector.getCoordinates(foodTruck.getDefaultAddress().getPostalCode(), foodTruck.getDefaultAddress().getStreet(),
                    foodTruck.getDefaultAddress().getStreetNumber(), foodTruck.getDefaultAddress().getCity());
            Coordinates coordinates = new Coordinates();
            coordinates.setLatitude(latLon.getLat());
            coordinates.setLongitude(latLon.getLon());
            foodTruck.setDefaultCoordinates(coordinates);
        }
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

    @Transactional
    @Override
    public FoodTruckDTO updateTruck(Long foodTruckId, FoodTruckUpdateDTO update) {
        final FoodTruck truck = getTruckById(foodTruckId);

        if (update.getName() != null) {
            truck.setName(update.getName());
        }
        if (update.getDescription() != null) {
            truck.setDescription(update.getDescription());
        }
        if (update.getSpeciality() != null) {
            truck.setSpeciality(update.getSpeciality());
        }
        truck.getCategories().clear();
        if(!update.getCategories().isEmpty()){
            truck.setCategories(new HashSet<>(categoryRepository.findAllById(update.getCategories().stream().map(CategoryDTO::getId).toList())));
        }

        if(update.getDefaultAddress() != null){
            final Address address = addressMapper.dtoToAddress(update.getDefaultAddress());
            if(!address.equals(truck.getDefaultAddress())){
                truck.setDefaultAddress(address);
                final LatLon latLon = openStreetMapConnector.getCoordinates(address.getPostalCode(), address.getStreet(),
                        address.getStreetNumber(), address.getCity());
                Coordinates coordinates = new Coordinates();
                coordinates.setLatitude(latLon.getLat());
                coordinates.setLongitude(latLon.getLon());
                truck.setDefaultCoordinates(coordinates);
            }
        }

        final FoodTruck updatedTruck = foodTruckRepo.save(truck);
        return mapper.foodTruckToDto(updatedTruck);
    }

    @Override
    public void updateProfileImage(Long foodTruckId, MultipartFile file) {
        if(file != null && !file.isEmpty()) {
            try {
                final FoodTruck foodTruck = foodTruckRepo.findById(foodTruckId)
                        .orElseThrow(() -> new IllegalArgumentException("FoodTruck " + foodTruckRepo + " does not exist"));
                final String oldImage = foodTruck.getProfileImage();
                final String imageLocation = uploadProfileImage(file, foodTruckId);
                foodTruck.setProfileImage(imageLocation);
                foodTruckRepo.save(foodTruck);
                if(oldImage != null){
                    final Path locationPath = Paths.get(foodTruckPicturesLocation, "trucks", String.valueOf(foodTruckId));
                    final Path oldImagePath = locationPath.resolve(imageLocation.substring(imageLocation.lastIndexOf("/")));
                    Files.deleteIfExists(oldImagePath);
                }
            } catch (IOException e) {
                logger.error("Failed to update image for foodTruck {}", foodTruckId, e);
                throw new IllegalStateException("Image upload failed");
            }
        }
    }

    @Override
    public void deleteTruck(long id) {
        foodTruckRepo.deleteById(id);
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
    public void openTruck(Long truckId, OpenFoodTruckRequestDTO request) {
        final FoodTruck truck = getTruckById(truckId);
        final Coordinates coordinates = new Coordinates();
        if(OpenFoodTruckMode.CURRENT_POSITION.equals(request.getOpenFoodTruckMode())){
            coordinates.setLatitude(request.getLatLng().getLat());
            coordinates.setLongitude(request.getLatLng().getLng());
        } else if(OpenFoodTruckMode.FOODTRUCK_ADDRESS.equals(request.getOpenFoodTruckMode())){
            coordinates.setLongitude(truck.getDefaultCoordinates().getLongitude());
            coordinates.setLatitude(truck.getDefaultCoordinates().getLatitude());
        } else if(OpenFoodTruckMode.CUSTOM_ADDRESS.equals(request.getOpenFoodTruckMode())){
            final LatLon latLon = openStreetMapConnector.getCoordinates(request.getCustomAddress().getPostalCode(), request.getCustomAddress().getStreet(),
                    request.getCustomAddress().getStreetNumber(), request.getCustomAddress().getCity());
            coordinates.setLatitude(latLon.getLat());
            coordinates.setLongitude(latLon.getLon());
        } else {
            throw new UnsupportedOperationException("Mode " + request.getOpenFoodTruckMode() + " not supported");
        }
        truck.setCoordinates(coordinates);
        truck.setOpen(true);
        foodTruckRepo.save(truck);
    }

    @Override
    public void closeTruck(Long truckId) {
        final FoodTruck truck = getTruckById(truckId);
        truck.setOpen(false);
        foodTruckRepo.save(truck);
    }

    @Override
    public boolean findStatusById(Long id) {
        return foodTruckRepo.findStatusById(id);

    }

    @Override
    public String uploadProfileImage(MultipartFile file, Long truckId) throws IOException {

        final Path locationPath = Paths.get(foodTruckPicturesLocation, "trucks", String.valueOf(truckId));

        if(!Files.exists(locationPath)) {
            Files.createDirectory(locationPath);
        }

        try {
            final String imagePath = UUID.randomUUID() + StringUtils.cleanPath(file.getOriginalFilename());
            final Path location = locationPath.resolve(imagePath);
            Files.copy(file.getInputStream(), location, StandardCopyOption.REPLACE_EXISTING);
            return Paths.get(foodTruckPicturesLocation,"trucks", ""+truckId, imagePath).toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Image upload failed");

        }

    }
}














