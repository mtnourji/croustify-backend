package com.example.rollingFoods.rollingFoodsApp.services;

import com.example.rollingFoods.rollingFoodsApp.dto.UserCredentialDTO;

public interface UserService {
    public UserCredentialDTO registerUser(UserCredentialDTO userCredentialDTO);
    public String signIn (UserCredentialDTO userCredentialDTO);
    public void validateToken(String token);
    public void updatePassword(UserCredentialDTO userCredentialDTO);


    public void deleteAccount(Long userCredentialId);
}
