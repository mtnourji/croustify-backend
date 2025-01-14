package com.croustify.backend.controllers;


import com.croustify.backend.dto.ItemDTO;
import com.croustify.backend.services.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8686")
@RestController
public class ItemsController {

    // Injecting the ItemService
    @Autowired
    private ItemService itemService;

    @Autowired
    private ObjectMapper objectMapper;



    // Get all items
    @GetMapping("/items")
    public ResponseEntity <List<ItemDTO>> getItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }
    // Get item by id
    @GetMapping("/items/{id}")
    public ResponseEntity <ItemDTO> getItemById(@PathVariable("id") Long itemId) {
        return ResponseEntity.ok(itemService.getItemById(itemId));
    }


    // Create item
    @PostMapping(value = "/items", consumes = "multipart/form-data")
    public ResponseEntity <ItemDTO> createItem(
            @RequestPart("itemDTO") MultipartFile itemDTO,
            @RequestParam("foodTruckId") Long foodTruckId,
            @RequestPart(value = "file" , required = false) MultipartFile file) throws IOException, java.io.IOException {

        ItemDTO item = objectMapper.readValue(itemDTO.getInputStream(), ItemDTO.class);
        final ItemDTO itemDTO1 = itemService.addItemToFoodTruck(item, foodTruckId, file);
        return ResponseEntity.ok(itemDTO1);

    }

    //Get items by food truck id
    @GetMapping("/items/foodTruck")
    public ResponseEntity <List<ItemDTO>> getItemsByFoodTruckId(@RequestParam("foodTruckId") Long foodTruckId) {
        return ResponseEntity.ok(itemService.getItemsByFoodTruckId(foodTruckId));
    }

    // Get items by category
    @GetMapping("/items/category")
    public ResponseEntity <List<ItemDTO>> getItemsByCategory(@RequestParam("category") String category) {
        return ResponseEntity.ok(itemService.getItemsByCategory(category));
    }

    // Get items by food truck id and category
    @GetMapping("/items/foodTruckAndCategory")
    public ResponseEntity<List<ItemDTO>> getItemsByFoodTruckIdAndCategory(
            @RequestParam("foodTruckId") Long foodTruckId,
            @RequestParam("category") String category) {
        return ResponseEntity.ok(itemService.getItemsByFoodTruckIdAndCategory(foodTruckId, category));
    }

    // Update item
    @PutMapping(value = "/items/{id}", consumes = "multipart/form-data")
    public ResponseEntity <ItemDTO> updateItem(@PathVariable("id") Long itemId, @RequestPart("itemDTO") ItemDTO itemDTO, @RequestPart (value="file") MultipartFile file) throws IOException, java.io.IOException {
        return ResponseEntity.ok(itemService.updateItem(itemId, itemDTO, file));
    }

    // Delete item
    @DeleteMapping("/items/{id}")
    public ResponseEntity <Void> deleteItem(@PathVariable("id") Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.ok().build();
    }

}
