package com.croustify.backend.repositories;

import com.croustify.backend.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepo extends JpaRepository<Menu, Long> {
    //List<Menu> findByTruckId(Long foodTruckId);
    //Find menu by truck id
    //Menu findByTruckId(Long truckId);
}
