package com.example.rollingFoods.rollingFoodsApp.services.imp;


import com.example.rollingFoods.rollingFoodsApp.dto.CategorieDTO;
import com.example.rollingFoods.rollingFoodsApp.mappers.CategorieMapper;
import com.example.rollingFoods.rollingFoodsApp.models.Categorie;
import com.example.rollingFoods.rollingFoodsApp.repositories.CategorieRepo;
import com.example.rollingFoods.rollingFoodsApp.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategorieServiceImp implements CategorieService {

    @Autowired
    private CategorieRepo categorieRepo;

    @Autowired
    private CategorieMapper mapper;

    public List<CategorieDTO> getAllCategories() {
        final List<Categorie> categories = categorieRepo.findAll();
        return categories.stream().map(mapper::categorieToDto).toList();
    }


    public CategorieDTO getCategorieById(Long id) {
        final Categorie categorie = categorieRepo.findById(id).orElse(new Categorie());
        return mapper.categorieToDto(categorie);
    }

    public CategorieDTO createCategorie(CategorieDTO categorieDTO) {
        final Categorie newCategorie = mapper.dtoToCategorie(categorieDTO);
        final Categorie saved = categorieRepo.save(newCategorie);
        return mapper.categorieToDto(saved);
    }

}
