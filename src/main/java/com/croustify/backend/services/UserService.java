package com.croustify.backend.services;

import com.croustify.backend.dto.UserCredentialDTO;
import com.croustify.backend.dto.UserLoginDTO;

public interface UserService {
    void registerUser(UserLoginDTO userCredentialDTO);
    String signIn (UserLoginDTO userCredentialDTO);

    void resetPassword(String token, String newPassword);

    void validateToken(String token);
    void updatePassword(UserCredentialDTO userCredentialDTO);


    void deleteAccount(Long userCredentialId);
}
