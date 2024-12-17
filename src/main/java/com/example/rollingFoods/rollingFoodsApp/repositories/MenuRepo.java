package com.example.rollingFoods.rollingFoodsApp.repositories;

import com.example.rollingFoods.rollingFoodsApp.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepo extends JpaRepository<Menu, Long> {
    //List<Menu> findByTruckId(Long foodTruckId);
    //Find menu by truck id
    //Menu findByTruckId(Long truckId);
}
