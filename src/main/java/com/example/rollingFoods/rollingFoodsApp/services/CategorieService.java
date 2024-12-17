package com.example.rollingFoods.rollingFoodsApp.services;

import com.example.rollingFoods.rollingFoodsApp.dto.CategorieDTO;
import com.example.rollingFoods.rollingFoodsApp.models.Categorie;

import java.util.List;

public interface CategorieService {

    public List<CategorieDTO> getAllCategories();
    public CategorieDTO getCategorieById(Long id);
    public CategorieDTO createCategorie(CategorieDTO categorieDTO);
}
