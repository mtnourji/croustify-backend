package com.example.rollingFoods.rollingFoodsApp.mappers;


import com.example.rollingFoods.rollingFoodsApp.dto.FoodTruckOwnerDTO;
import com.example.rollingFoods.rollingFoodsApp.models.FoodTruckOwner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = "spring")
public interface FoodTruckOwnerMapper {

    FoodTruckOwner dtoToFoodTruckOwner(FoodTruckOwnerDTO dto);

    FoodTruckOwnerDTO foodTruckOwnerToDto(FoodTruckOwner foodTruckOwner);


}
