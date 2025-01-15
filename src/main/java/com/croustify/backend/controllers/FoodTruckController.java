package com.croustify.backend.controllers;

import com.croustify.backend.dto.FoodTruckDTO;
import com.croustify.backend.services.TruckService;
import com.croustify.backend.validation.OwnUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/foodTrucks")
    public ResponseEntity<List<FoodTruckDTO>> getFoodTrucks(@RequestParam(name = "onlyFavorites", required = false, defaultValue = "false") boolean onlyFavorites) {
        return ResponseEntity.ok(truckService.getTrucks(onlyFavorites));
    }

    @GetMapping("/foodTrucks/search")
    public ResponseEntity<List<FoodTruckDTO>> searchFoodTrucks(
            @RequestParam(required = false) Boolean isOpen,
            @RequestParam(required = false) List<Long> categoryIds,
            @RequestParam(required = false) Boolean onlyFavorites
    ) {
        List<FoodTruckDTO> foodTrucks = truckService.searchFoodTrucks(isOpen, categoryIds, onlyFavorites);
        return ResponseEntity.ok(foodTrucks);
    }

    // Get food truck by id
    @GetMapping("/foodTrucks/{id}")
    private FoodTruckDTO findById(@PathVariable Long id) {
        return truckService.findTruckById(id);
    }


    @OwnUser
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_OWNER')")
    @PostMapping(value = "/users/{userId}/foodTrucks", consumes = "multipart/form-data")
    public ResponseEntity<FoodTruckDTO> createFoodTruck(
            @PathVariable("userId") Long ownerId,
            @RequestPart("foodTruck") FoodTruckDTO foodTruck,
            @RequestPart("file") MultipartFile imageFile
    ) throws IOException {
        final FoodTruckDTO createdFoodTruck = truckService.createTruck(foodTruck, ownerId, imageFile);
        return ResponseEntity.created(URI.create("/foodTrucks/" + createdFoodTruck.getId())).build();
    }

    // Update food truck
    @PutMapping(value = "/updateFoodTruck", consumes = "multipart/form-data")
    public ResponseEntity<FoodTruckDTO> updateFoodTruck(@RequestParam("ownerId") Long id, @RequestPart("foodTruck") MultipartFile foodTruckFile) throws IOException {
        FoodTruckDTO foodTruck = objectMapper.readValue(foodTruckFile.getInputStream(), FoodTruckDTO.class);
        final FoodTruckDTO updatedFoodTruck = truckService.updateTruck(id, foodTruck);
        return ResponseEntity.ok(updatedFoodTruck);
    }


    // Get food truck by owner id
    @GetMapping("/foodTruckByOwnerId")
    public ResponseEntity<Long> getFoodTruckByOwnerId(@RequestParam("ownerId") Long ownerId) {
        return ResponseEntity.ok(truckService.getTruckByOwnerId(ownerId));
    }

    // Delete food truck
    @DeleteMapping("/deleteFoodTruck")
    public ResponseEntity<Void> deleteFoodTruck(@RequestParam("foodTruckId") int id) {
        truckService.deleteTruck(id);
        return ResponseEntity.noContent().build();
    }

    // Rate food truck
    @PutMapping("/rateFoodTruck")
    public ResponseEntity<FoodTruckDTO> rateFoodTruck(@RequestParam("foodTruckId") Long truckId, @RequestParam("rating") int rating) {
        final FoodTruckDTO ratedFoodTruck = truckService.rateTruck(truckId, rating);
        return ResponseEntity.ok(ratedFoodTruck);
    }

    // Open food truck
    @PutMapping("/openFoodTruck")
    public ResponseEntity<FoodTruckDTO> openFoodTruck(@RequestParam("foodTruckId") Long truckId, @RequestBody FoodTruckDTO foodTruckDTO) {
        final FoodTruckDTO openedFoodTruck = truckService.openTruck(truckId, foodTruckDTO);
        return ResponseEntity.ok(openedFoodTruck);
    }

    // Close food truck
    @PutMapping("/closeFoodTruck")
    public ResponseEntity<FoodTruckDTO> closeFoodTruck(@RequestParam("foodTruckId") Long truckId) {
        final FoodTruckDTO closedFoodTruck = truckService.closeTruck(truckId);
        return ResponseEntity.ok(closedFoodTruck);
    }

    // Find if food truck is open
    @GetMapping("/isFoodTruckOpen")
    public ResponseEntity<Boolean> isFoodTruckOpen(@RequestParam("foodTruckId") Long id) {
        return ResponseEntity.ok(truckService.findStatusById(id));
    }

    //upload image
    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("foodTruckId") Long foodTruckId) {

        try {
            truckService.uploadProfileImage(file, foodTruckId);

            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Image upload failed");
        }
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

