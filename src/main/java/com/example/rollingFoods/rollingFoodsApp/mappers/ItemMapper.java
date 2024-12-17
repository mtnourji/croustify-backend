package com.example.rollingFoods.rollingFoodsApp.mappers;


import com.example.rollingFoods.rollingFoodsApp.dto.ItemDTO;
import com.example.rollingFoods.rollingFoodsApp.models.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {
    @Mapping(target = "id", ignore = true)
    Item dtoToItem(ItemDTO dto);
    ItemDTO itemToDto(Item item);
}
