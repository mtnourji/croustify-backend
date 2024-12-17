package com.example.rollingFoods.rollingFoodsApp.repositories;

import com.example.rollingFoods.rollingFoodsApp.models.LocationOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationOwnerRepo extends JpaRepository<LocationOwner, Long> {
}
