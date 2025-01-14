package com.croustify.backend.mappers;


import com.croustify.backend.dto.LocationOwnerDTO;
import com.croustify.backend.models.LocationOwner;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationOwnerMapper {

        LocationOwner toLocationOwner(LocationOwnerDTO locationOwnerDTO);

        LocationOwnerDTO toLocationOwnerDTO(LocationOwner locationOwner);
}
