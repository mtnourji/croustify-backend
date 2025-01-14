package com.croustify.backend.repositories;

import com.croustify.backend.models.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepo extends JpaRepository<Categorie, Long> {
    //Find all categories by menu id
    //List<Categorie> findAllByMenuId(Long menuId);
}
