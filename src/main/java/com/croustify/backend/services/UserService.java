package com.croustify.backend.services;

import com.croustify.backend.dto.UserCredentialDTO;

public interface UserService {
    void registerUser(UserCredentialDTO userCredentialDTO);
    String signIn (UserCredentialDTO userCredentialDTO);
    void validateToken(String token);
    void updatePassword(UserCredentialDTO userCredentialDTO);


    void deleteAccount(Long userCredentialId);
}
