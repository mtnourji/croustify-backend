package com.croustify.backend.services;

import com.croustify.backend.dto.CategorieDTO;

import java.util.List;

public interface CategorieService {

    public List<CategorieDTO> getAllCategories();
    public CategorieDTO getCategorieById(Long id);
    public CategorieDTO createCategorie(CategorieDTO categorieDTO);
}
