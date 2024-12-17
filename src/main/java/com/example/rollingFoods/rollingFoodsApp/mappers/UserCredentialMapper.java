package com.example.rollingFoods.rollingFoodsApp.mappers;


import com.example.rollingFoods.rollingFoodsApp.dto.UserCredentialDTO;
import com.example.rollingFoods.rollingFoodsApp.models.UserCredential;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserCredentialMapper {

    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "enabled", target = "enabled", defaultValue = "false")
    @Mapping(source = "roles", target = "roles")

    UserCredentialDTO userToDto(UserCredential user);
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "enabled", target = "enabled", defaultValue = "false")
    @Mapping(source = "roles", target = "roles")
    UserCredential dtoToUser(UserCredentialDTO dto);




}
