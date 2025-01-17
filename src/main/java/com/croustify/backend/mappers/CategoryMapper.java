package com.croustify.backend.mappers;


import com.croustify.backend.dto.CategoryDTO;
import com.croustify.backend.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    @Mapping(target = "id", ignore = true)
    Category dtoToCategory(CategoryDTO dto);
    CategoryDTO categoryToDto(Category category);
    List<CategoryDTO> categoryToDto(List<Category> category);

}