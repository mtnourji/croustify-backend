package com.croustify.backend.controllers;

import com.croustify.backend.dto.FoodTruckDTO;
import com.croustify.backend.dto.FoodTruckUpdateDTO;
import com.croustify.backend.dto.OpenFoodTruckRequestDTO;
import com.croustify.backend.services.TruckService;
import com.croustify.backend.validation.OwnFoodTruck;
import com.croustify.backend.validation.OwnUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class FoodTruckController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TruckService truckService;


    @OwnUser
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_OWNER')")
    @GetMapping("/users/{userId}/foodTrucks")
    public ResponseEntity<List<FoodTruckDTO>> getOwnerFoodTrucks(@PathVariable(name = "userId") long userId) {
        return ResponseEntity.ok(truckService.getOwnerTrucks(userId));
    }

    @OwnUser
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_OWNER')")
    @GetMapping("/users/{userId}/foodTrucks/{foodTruckId}")
    public ResponseEntity<FoodTruckDTO> getOwnerFoodTrucks(@PathVariable(name = "userId") long userId,@PathVariable(name = "foodTruckId") long foodTruckId) {
        return ResponseEntity.ok(truckService.getOwnerTruck(userId, foodTruckId));
    }

    @GetMapping("/foodTrucks")
    public ResponseEntity<List<FoodTruckDTO>> getFoodTrucks(@RequestParam(name = "onlyFavorites", required = false, defaultValue = "false") boolean onlyFavorites) {
        return ResponseEntity.ok(truckService.getTrucks(onlyFavorites));
    }

    @GetMapping("/foodTrucks/search")
    public ResponseEntity<List<FoodTruckDTO>> searchFoodTrucks(
            @RequestParam(required = false) Boolean isOpen,
            @RequestParam(required = false) List<Long> categoryIds,
            @RequestParam(required = false) Boolean onlyFavorites,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lng,
            @RequestParam(required = false) Integer radiusInKm) {
        if((lat == null && lng != null) || (lat != null && lng == null)){
            throw new IllegalArgumentException("Invalid request, lat and lng must be set together");
        }
        List<FoodTruckDTO> foodTrucks = truckService.searchFoodTrucks(isOpen, categoryIds, onlyFavorites, lat, lng, radiusInKm);
        return ResponseEntity.ok(foodTrucks);
    }

    @OwnUser
    @Secured("ROLE_FOOD_TRUCK_OWNER")
    @PostMapping(value = "/users/{userId}/foodTrucks", consumes = "multipart/form-data")
    public ResponseEntity<FoodTruckDTO> createFoodTruck(
            @PathVariable("userId") Long ownerId,
            @RequestPart("foodTruck") FoodTruckDTO foodTruck,
            @RequestPart("file") MultipartFile imageFile
    ) throws IOException {
        final FoodTruckDTO createdFoodTruck = truckService.createTruck(foodTruck, ownerId, imageFile);
        return ResponseEntity.created(URI.create("/foodTrucks/" + createdFoodTruck.getId())).build();
    }

    @OwnFoodTruck
    @Secured("ROLE_FOOD_TRUCK_OWNER")
    @PutMapping("/foodTrucks/{foodTruckId}")
    public ResponseEntity<FoodTruckDTO> updateFoodTruck(@PathVariable("foodTruckId") long foodTruckId, @RequestBody FoodTruckUpdateDTO update) {
        return ResponseEntity.ok(truckService.updateTruck(foodTruckId, update));
    }

    @OwnFoodTruck
    @Secured("ROLE_FOOD_TRUCK_OWNER")
    @PutMapping("/foodTrucks/{foodTruckId}/profileImage")
    public ResponseEntity<Void> updateProfileImage(@PathVariable("foodTruckId") Long foodTruckId, @RequestParam("image") MultipartFile file) {
        truckService.updateProfileImage(foodTruckId, file);
        return ResponseEntity.noContent().build();
    }

    @OwnFoodTruck
    @Secured("ROLE_FOOD_TRUCK_OWNER")
    @DeleteMapping("/foodTrucks/{foodTruckId}")
    public ResponseEntity<Void> deleteFoodTruck(@RequestParam("foodTruckId") long foodTruckId) {
        truckService.deleteTruck(foodTruckId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/rateFoodTruck")
    public ResponseEntity<FoodTruckDTO> rateFoodTruck(@RequestParam("foodTruckId") Long truckId, @RequestParam("rating") int rating) {
        final FoodTruckDTO ratedFoodTruck = truckService.rateTruck(truckId, rating);
        return ResponseEntity.ok(ratedFoodTruck);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_FOOD_TRUCK_OWNER" })
    @OwnFoodTruck
    @PostMapping("/foodTrucks/{foodTruckId}/open")
    public ResponseEntity<Void> openFoodTruck(@PathVariable("foodTruckId") Long foodTruckId, @RequestBody OpenFoodTruckRequestDTO request) {
        truckService.openTruck(foodTruckId, request);
        return ResponseEntity.ok().build();
    }

    @Secured({ "ROLE_ADMIN", "ROLE_FOOD_TRUCK_OWNER" })
    @OwnFoodTruck
    @PostMapping("/foodTrucks/{foodTruckId}/close")
    public ResponseEntity<Void> closeFoodTruck(@PathVariable("foodTruckId") Long foodTruckId) {
        truckService.closeTruck(foodTruckId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/isFoodTruckOpen")
    public ResponseEntity<Boolean> isFoodTruckOpen(@RequestParam("foodTruckId") Long id) {
        return ResponseEntity.ok(truckService.findStatusById(id));
    }

    @GetMapping("/images/{folder}/{filename}")
    public ResponseEntity<byte[]> serveFile(@PathVariable String folder, @PathVariable String filename) {
        try {
            // Construire le chemin du fichier
            Path file = Paths.get("D:/Projet rollingFoodsApp/pictures/foodTruck/" + folder).resolve(filename);

            // Vérifier que le fichier existe et est lisible
            if (Files.exists(file) && Files.isReadable(file)) {
                // Lire le fichier en tant que tableau d'octets
                byte[] fileData = Files.readAllBytes(file);

                // Déterminer le type MIME du fichier
                String mimeType = Files.probeContentType(file);

                // Renvoyer la réponse avec le fichier en tant que tableau d'octets
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                        .contentType(MediaType.parseMediaType(mimeType))
                        .body(fileData);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (IOException e) {
            // Gérer les erreurs d'entrée/sortie
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

