package com.croustify.backend.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
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
    private String description;

    private BigDecimal priceTtc;
    @ManyToOne
    @JoinColumn(name = "id_food_truck")
    @JsonBackReference
    private FoodTruck foodTruck;

    @ManyToMany
    @JoinTable(
            name = "menu_menu_category",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
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

    public BigDecimal getPriceTtc() {
        return priceTtc;
    }

    public void setPriceTtc(BigDecimal priceTtc) {
        this.priceTtc = priceTtc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}