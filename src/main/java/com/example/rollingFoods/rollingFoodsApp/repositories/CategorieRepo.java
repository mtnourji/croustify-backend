package com.example.rollingFoods.rollingFoodsApp.repositories;

import com.example.rollingFoods.rollingFoodsApp.models.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategorieRepo extends JpaRepository<Categorie, Long> {
    //Find all categories by menu id
    //List<Categorie> findAllByMenuId(Long menuId);
}
