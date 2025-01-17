package com.croustify.backend.mappers;


import com.croustify.backend.dto.MenuCategoryDTO;
import com.croustify.backend.models.MenuCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MenuCategoryMapper {
    @Mapping(target = "id", ignore = true)
    MenuCategory dtoToCategory(MenuCategoryDTO dto);
    MenuCategoryDTO categoryToDto(MenuCategory category);
    List<MenuCategoryDTO> categoryToDto(List<MenuCategory> category);

}