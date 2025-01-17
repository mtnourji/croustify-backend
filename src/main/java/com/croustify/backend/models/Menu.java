package com.croustify.backend.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_menu")
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_food_truck")
    @JsonBackReference
    private FoodTruck foodTruck;

    @ManyToMany
    @JoinTable(
            name = "menu_categorie",
            joinColumns = @JoinColumn(name = "id_menu"),
            inverseJoinColumns = @JoinColumn(name = "id_categorie")
    )
    private Set<MenuCategory> menuCategories = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FoodTruck getFoodTruck() {
        return foodTruck;
    }

    public void setFoodTruck(FoodTruck foodTruck) {
        this.foodTruck = foodTruck;
    }

    public Set<MenuCategory> getMenuCategories() {
        return menuCategories;
    }

    public void setMenuCategories(Set<MenuCategory> menuCategories) {
        this.menuCategories = menuCategories;
    }
}