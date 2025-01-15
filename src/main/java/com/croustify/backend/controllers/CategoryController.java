package com.croustify.backend.controllers;

import com.croustify.backend.dto.CategoryDTO;
import com.croustify.backend.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO newCategory) {
        final CategoryDTO category = categoryService.createCategory(newCategory);
        return ResponseEntity.ok(category);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
