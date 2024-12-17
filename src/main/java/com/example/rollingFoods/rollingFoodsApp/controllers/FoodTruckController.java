package com.example.rollingFoods.rollingFoodsApp.controllers;

import ch.qos.logback.core.util.StringUtil;
import com.example.rollingFoods.rollingFoodsApp.dto.FoodTruckDTO;
import com.example.rollingFoods.rollingFoodsApp.enums.FoodType;
import com.example.rollingFoods.rollingFoodsApp.models.FoodTruck;
import com.example.rollingFoods.rollingFoodsApp.services.TruckService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.opencensus.resource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
@CrossOrigin(origins = "http://localhost:8686")
@RestController
public class FoodTruckController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TruckService truckService;


    // Get all food trucks
    @GetMapping("/foodTruck")
    public ResponseEntity<List<FoodTruckDTO>> getFoodTruck() {
        return ResponseEntity.ok(truckService.getTrucks());
    }

    // Get food truck by id
    @GetMapping("/foodTruck/{id}")
    private FoodTruckDTO findById(@PathVariable Long id) {
        return truckService.findTruckById(id);
    }


    //Create food truck
    @PostMapping(value = "/addFoodTruck", consumes = "multipart/form-data")
    public ResponseEntity<FoodTruckDTO> createFoodTruck(
            @RequestParam("ownerId") Long ownerId,
            @RequestPart("foodTruck") MultipartFile foodTruckFile,
            @RequestPart("file") MultipartFile imageFile
    ) throws IOException {
        FoodTruckDTO foodTruck = objectMapper.readValue(foodTruckFile.getInputStream(), FoodTruckDTO.class);
        final FoodTruckDTO createdFoodTruck = truckService.createTruck(foodTruck, ownerId, imageFile);
        return ResponseEntity.created(URI.create("/foodTruck/" + createdFoodTruck.getId())).build();
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

    // Find food truck by food type
    @GetMapping("/foodTruckByFoodType")
    public ResponseEntity<List<FoodTruckDTO>> getFoodTruckByFoodType(@RequestParam("foodType") String foodType) {
       try{
           FoodType type = FoodType.valueOf(foodType.toUpperCase().replace(" ","_"));
           List<FoodTruckDTO> foodTrucks = truckService.findByFoodType(type);
           return new ResponseEntity<>(foodTrucks,HttpStatus.OK);
       }catch (IllegalArgumentException e){
           return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }


    }

    // Find food truck by name and food type and description
    @GetMapping("/searchFoodTrucks")
    public ResponseEntity<List<FoodTruckDTO>> getFoodTruckByNameAndFoodTypeAndDescription(@RequestParam("searchTerm") String searchTerm) {
        return ResponseEntity.ok(truckService.findByNameAndFoodTypeAndDescription(searchTerm));
    }



}

