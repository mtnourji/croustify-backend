package com.croustify.backend.mappers;


import com.croustify.backend.dto.FoodTruckOwnerDTO;
import com.croustify.backend.models.FoodTruckOwner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FoodTruckOwnerMapper {

    FoodTruckOwner dtoToFoodTruckOwner(FoodTruckOwnerDTO dto);

    FoodTruckOwnerDTO foodTruckOwnerToDto(FoodTruckOwner foodTruckOwner);


}
