package com.croustify.backend.repositories;

import com.croustify.backend.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepo extends JpaRepository<Menu, Long> {
    List<Menu> findAllByFoodTruckId(long foodTruckId);
}
