package com.croustify.backend.controllers;

import com.croustify.backend.dto.MenuCategoryDTO;
import com.croustify.backend.services.MenuCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenuCategoryController {

    @Autowired
    private MenuCategoryService categoryService;
    @Secured({ "ROLE_ADMIN", "ROLE_FOOD_TRUCK_OWNER" })
    @GetMapping("/menuCategories")
    public ResponseEntity<List<MenuCategoryDTO>> getAllCategories() {
        List<MenuCategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/menuCategories")
    public ResponseEntity<MenuCategoryDTO> createCategory(@RequestBody MenuCategoryDTO newCategory) {
        final MenuCategoryDTO category = categoryService.createCategory(newCategory);
        return ResponseEntity.ok(category);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/menuCategories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
