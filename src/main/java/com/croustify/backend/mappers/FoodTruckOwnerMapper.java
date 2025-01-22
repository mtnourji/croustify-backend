package com.croustify.backend.mappers;


import com.croustify.backend.dto.NewFoodTruckOwnerDTO;
import com.croustify.backend.models.FoodTruckOwner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FoodTruckOwnerMapper {

    @Mapping(source = "phone", target = "phoneNumber")
    @Mapping(source = "firstName", target = "firstname")
    @Mapping(source = "lastName", target = "lastname")
    @Mapping(source = "tvaNumber", target = "tva")
    FoodTruckOwner newFoodTruckOwnerToModel(NewFoodTruckOwnerDTO foodTruckOwnerRequest);
}
