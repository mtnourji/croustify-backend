package com.croustify.backend.controllers;


import com.croustify.backend.dto.CategoryMenusDTO;
import com.croustify.backend.dto.MenuCreationDTO;
import com.croustify.backend.dto.MenuDTO;
import com.croustify.backend.services.MenuService;
import com.croustify.backend.validation.OwnFoodTruck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/menus/{id}")
    public ResponseEntity <MenuDTO> getMenuById(@PathVariable("id")  Long id) {
        return ResponseEntity.ok(menuService.getMenuById(id));
    }


    @OwnFoodTruck
    @Secured("ROLE_FOOD_TRUCK_OWNER")
    @PostMapping("/foodTrucks/{foodTruckId}/menus")
    public ResponseEntity <MenuDTO> createMenu(@PathVariable("foodTruckId") Long foodTruckId, @RequestBody MenuCreationDTO menuDTO) {
        return ResponseEntity.ok(menuService.createMenu(foodTruckId, menuDTO));
    }

    @GetMapping("/foodTrucks/{foodTruckId}/menus")
    public ResponseEntity<List<CategoryMenusDTO>> getMenusByTruckId(@PathVariable("foodTruckId") Long foodTruckId) {
        return ResponseEntity.ok(menuService.getFoodTruckMenus(foodTruckId));
    }
}


