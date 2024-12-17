package com.example.rollingFoods.rollingFoodsApp.repositories;

import com.example.rollingFoods.rollingFoodsApp.enums.ItemCategorie;
import com.example.rollingFoods.rollingFoodsApp.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepo extends JpaRepository<Item, Long> {

    // Find all items by category
    @Query("SELECT i FROM Item i WHERE i.itemCategorie = :categorie")
    List<Item> findByItemCategorie(@Param("categorie") ItemCategorie categorie);

    // Find all items by food truck id
    List<Item> findByFoodTruckId(Long foodTruckId);

    // Find all items by food truck id and category
    @Query("SELECT i FROM Item i WHERE i.foodTruck.id = :foodTruckId AND i.itemCategorie = :itemCategorie")
    List<Item> findByFoodTruckIdAndItemCategorie(@Param("foodTruckId") Long foodTruckId, @Param("itemCategorie") ItemCategorie itemCategorie);


}
