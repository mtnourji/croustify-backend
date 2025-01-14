package com.croustify.backend.mappers;


import com.croustify.backend.dto.FoodTruckDTO;
import com.croustify.backend.models.FoodTruck;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FoodTruckMapper {

    FoodTruck dtoToFoodTruck(FoodTruckDTO dto);
    FoodTruckDTO foodTruckToDto(FoodTruck foodTruck);
}
