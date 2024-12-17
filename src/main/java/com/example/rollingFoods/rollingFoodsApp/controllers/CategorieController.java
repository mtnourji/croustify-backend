package com.example.rollingFoods.rollingFoodsApp.controllers;


import com.example.rollingFoods.rollingFoodsApp.dto.CategorieDTO;
import com.example.rollingFoods.rollingFoodsApp.services.CategorieService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategorieController {

    private static final Logger logger =  LoggerFactory.getLogger(CategorieController.class);

    // Injecting the CategorieService
    @Autowired
    private CategorieService categorieService;
    // Get all categories
    @GetMapping("/categories")
    public ResponseEntity <List<CategorieDTO>> getCategories() {
        logger.info("Getting all categories");
        if (categorieService.getAllCategories().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorieService.getAllCategories());

    }
    // Get category by id
    @GetMapping("/categories/{id}")
    public ResponseEntity <CategorieDTO> getCategoryById(Long id){
        return ResponseEntity.ok(categorieService.getCategorieById(id));
    }
    // Create category
    @PostMapping("/categorie")
    public ResponseEntity<CategorieDTO> createCategorie(@RequestBody CategorieDTO categorieDTO) {
        return ResponseEntity.ok(categorieService.createCategorie(categorieDTO)) ;
    }


}
