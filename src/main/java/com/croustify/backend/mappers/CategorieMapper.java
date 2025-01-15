package com.croustify.backend.mappers;


import com.croustify.backend.dto.CategorieDTO;
import com.croustify.backend.dto.CategoryDTO;
import com.croustify.backend.models.Categorie;
import com.croustify.backend.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategorieMapper {
    @Mapping(target = "id", ignore = true)
    Categorie dtoToCategorie(CategorieDTO dto);
    CategorieDTO categorieToDto(Categorie categorie);

    @Mapping(target = "id", ignore = true)
    Category dtoToCategory(CategoryDTO dto);
    CategoryDTO categoryToDto(Category category);
    List<CategoryDTO> categoryToDto(List<Category> category);

}