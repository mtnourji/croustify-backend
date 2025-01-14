package com.croustify.backend.services.imp;


import com.croustify.backend.dto.CategorieDTO;
import com.croustify.backend.repositories.CategorieRepo;
import com.croustify.backend.mappers.CategorieMapper;
import com.croustify.backend.models.Categorie;
import com.croustify.backend.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
