package com.croustify.backend.repositories;

import com.croustify.backend.models.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepo extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserCredentialId(long userCredentialId);
    Favorite findByUserCredentialIdAndFoodTruckId(long userCredential, long foodTruck);
    Favorite getReferenceByUserCredentialIdAndId(long userCredential, long id);
    boolean existsByUserCredentialIdAndFoodTruckId(long userCredential, long foodTruck);
}
