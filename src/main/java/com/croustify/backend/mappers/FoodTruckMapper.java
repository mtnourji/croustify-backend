package com.croustify.backend.mappers;


import com.croustify.backend.dto.FoodTruckDTO;
import com.croustify.backend.models.FoodTruck;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FoodTruckMapper {

    @Mapping(target = "id", ignore = true)
    FoodTruck dtoToFoodTruck(FoodTruckDTO dto);
    FoodTruckDTO foodTruckToDto(FoodTruck foodTruck);

    List<FoodTruckDTO> foodTruckToDto(List<FoodTruck> all);
}
