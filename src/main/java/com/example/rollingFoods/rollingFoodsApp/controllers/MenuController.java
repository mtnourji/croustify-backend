package com.example.rollingFoods.rollingFoodsApp.controllers;


import com.example.rollingFoods.rollingFoodsApp.dto.MenuDTO;
import com.example.rollingFoods.rollingFoodsApp.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8686")
@RestController
@RequestMapping("/menu")
public class MenuController {

    // Injecting the MenuService
    @Autowired
    private MenuService menuService;

    // Get all menus
    @GetMapping("/menus")
    public ResponseEntity <List<MenuDTO>> getMenus() {
        return ResponseEntity.ok(menuService.getAllMenus());
    }
    // Get menu by id
    @GetMapping("/menus/{id}")
    public ResponseEntity <MenuDTO> getMenuById(@PathVariable("id")  Long id) {
        return ResponseEntity.ok(menuService.getMenuById(id));
    }
    // Create menu
    @PostMapping("/menus")
    public ResponseEntity <MenuDTO> createMenu(@RequestBody MenuDTO menuDTO) {
        return ResponseEntity.ok(menuService.createMenu(menuDTO));
    }
    // Get menu by truck id

    /*
    @GetMapping("/menus/truck/{foodTruckId}")
    public MenuDTO getMenusByTruckId(@PathVariable("foodTruckId") Long foodTruckId) {
        return menuService.getMenusByTruckId(foodTruckId);
    }

     */
}


