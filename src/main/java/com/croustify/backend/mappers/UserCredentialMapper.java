package com.croustify.backend.mappers;


import com.croustify.backend.dto.UserCredentialDTO;
import com.croustify.backend.dto.UserDTO;
import com.croustify.backend.dto.UserLoginDTO;
import com.croustify.backend.models.User;
import com.croustify.backend.models.UserCredential;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserCredentialMapper {

    @Mapping(ignore = true, target = "password")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "enabled", target = "enabled", defaultValue = "false")
    @Mapping(source = "roles", target = "roles")
    UserCredentialDTO userToDto(UserCredential user);

    UserCredential dtoToUser(UserLoginDTO dto);

    UserDTO rawUserToDto(User user);
    List<UserDTO> rawUserToDto(List<User> user);

}
