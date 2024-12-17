package com.example.rollingFoods.rollingFoodsApp.mappers;


import com.example.rollingFoods.rollingFoodsApp.dto.FoodTruckDTO;
import com.example.rollingFoods.rollingFoodsApp.models.FoodTruck;
import org.mapstruct.Mapping;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FoodTruckMapper {

    FoodTruck dtoToFoodTruck(FoodTruckDTO dto);
    FoodTruckDTO foodTruckToDto(FoodTruck foodTruck);
}
