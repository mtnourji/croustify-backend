package com.croustify.backend.mappers;


import com.croustify.backend.dto.ItemDTO;
import com.croustify.backend.models.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {
    @Mapping(target = "id", ignore = true)
    Item dtoToItem(ItemDTO dto);
    ItemDTO itemToDto(Item item);
}
