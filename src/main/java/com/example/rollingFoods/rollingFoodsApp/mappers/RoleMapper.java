package com.example.rollingFoods.rollingFoodsApp.mappers;


import com.example.rollingFoods.rollingFoodsApp.dto.RoleDTO;
import com.example.rollingFoods.rollingFoodsApp.models.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {

    Role dtoToRole(RoleDTO dto);
    RoleDTO roleToDto(Role role);
}
