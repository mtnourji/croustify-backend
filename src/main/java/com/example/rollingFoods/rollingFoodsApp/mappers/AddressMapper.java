package com.example.rollingFoods.rollingFoodsApp.mappers;


import com.example.rollingFoods.rollingFoodsApp.dto.AddressDTO;
import com.example.rollingFoods.rollingFoodsApp.models.embedded.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDTO addressToDto(Address address);
    Address dtoToAddress(AddressDTO dto);
}
