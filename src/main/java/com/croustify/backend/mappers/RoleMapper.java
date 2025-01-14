package com.croustify.backend.mappers;


import com.croustify.backend.dto.RoleDTO;
import com.croustify.backend.models.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {

    Role dtoToRole(RoleDTO dto);
    RoleDTO roleToDto(Role role);
}
