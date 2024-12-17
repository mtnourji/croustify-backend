package com.example.rollingFoods.rollingFoodsApp.mappers;


import com.example.rollingFoods.rollingFoodsApp.dto.MenuDTO;
import com.example.rollingFoods.rollingFoodsApp.models.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MenuMapper {
    @Mapping(target = "id", ignore = true)
    Menu dtoToMenu(MenuDTO dto);
    MenuDTO menuToDto(Menu menu);
}
