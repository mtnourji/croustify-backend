package com.example.rollingFoods.rollingFoodsApp.mappers;


import com.example.rollingFoods.rollingFoodsApp.dto.CategorieDTO;
import com.example.rollingFoods.rollingFoodsApp.models.Categorie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategorieMapper {
    @Mapping(target = "id", ignore = true)
    Categorie dtoToCategorie(CategorieDTO dto);
    CategorieDTO categorieToDto(Categorie categorie);
}