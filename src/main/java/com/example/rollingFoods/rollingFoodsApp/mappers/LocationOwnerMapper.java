package com.example.rollingFoods.rollingFoodsApp.mappers;


import com.example.rollingFoods.rollingFoodsApp.dto.LocationOwnerDTO;
import com.example.rollingFoods.rollingFoodsApp.models.LocationOwner;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationOwnerMapper {

        LocationOwner toLocationOwner(LocationOwnerDTO locationOwnerDTO);

        LocationOwnerDTO toLocationOwnerDTO(LocationOwner locationOwner);
}
