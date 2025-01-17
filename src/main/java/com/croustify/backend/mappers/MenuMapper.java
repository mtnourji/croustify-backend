package com.croustify.backend.mappers;


import com.croustify.backend.dto.MenuCreationDTO;
import com.croustify.backend.dto.MenuDTO;
import com.croustify.backend.models.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MenuMapper {
    @Mapping(target = "id", ignore = true)
    Menu dtoToMenu(MenuDTO dto);
    @Mapping(source = "menuCategories", target = "categories")
    MenuDTO menuToDto(Menu menu);

    Menu dtoToMenu(MenuCreationDTO menuDTO);
}
