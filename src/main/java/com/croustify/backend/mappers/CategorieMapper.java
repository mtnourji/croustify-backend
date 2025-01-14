package com.croustify.backend.mappers;


import com.croustify.backend.dto.CategorieDTO;
import com.croustify.backend.models.Categorie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategorieMapper {
    @Mapping(target = "id", ignore = true)
    Categorie dtoToCategorie(CategorieDTO dto);
    CategorieDTO categorieToDto(Categorie categorie);
}