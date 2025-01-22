package com.croustify.backend.services;

import com.croustify.backend.bean.UserRole;
import com.croustify.backend.dto.UserCredentialDTO;
import com.croustify.backend.dto.UserDTO;
import com.croustify.backend.dto.UserLoginDTO;
import com.croustify.backend.models.User;

import java.util.List;

public interface UserService {
    void registerUser(UserLoginDTO userCredentialDTO);
    String signIn (UserLoginDTO userCredentialDTO);

    void resetPassword(String token, String newPassword);

    void validateToken(String token);
    void updatePassword(UserCredentialDTO userCredentialDTO);


    void deleteAccount(Long userCredentialId);

    List<UserDTO> findUsers(UserRole userType, String email, String firstName, String lastName, Boolean enabled);
}
