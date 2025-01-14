package com.croustify.backend.mappers;


import com.croustify.backend.dto.AddressDTO;
import com.croustify.backend.models.embedded.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDTO addressToDto(Address address);
    Address dtoToAddress(AddressDTO dto);
}
