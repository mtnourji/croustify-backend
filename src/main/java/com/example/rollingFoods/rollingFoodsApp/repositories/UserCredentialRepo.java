package com.example.rollingFoods.rollingFoodsApp.repositories;

import com.example.rollingFoods.rollingFoodsApp.models.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserCredentialRepo extends JpaRepository<UserCredential, Long> {
    UserCredential findByEmail(String email);
    UserCredential findByUsername(String username);

    boolean existsByEmail(String email);
}
