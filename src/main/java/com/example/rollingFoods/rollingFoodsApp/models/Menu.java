package com.example.rollingFoods.rollingFoodsApp.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_menu")
    private Long id;
    private String name;

    //Relation ManyToOne avec FoodTruck car un menu appartient à un foodTruck
    @ManyToOne
    @JoinColumn(name = "id_food_truck")
    @JsonBackReference
    private FoodTruck foodTruck;


    //Relation ManyToMany avec Categorie car un menu peut contenir plusieurs catégories
    @ManyToMany
    @JoinTable(
            name = "menu_categorie",
            joinColumns = @JoinColumn(name = "id_menu"),
            inverseJoinColumns = @JoinColumn(name = "id_categorie")
    )
    @JsonManagedReference
    private Set<Categorie> categories = new HashSet<>();

    public Menu() {
    }

    public Menu(Long id, FoodTruck foodTruck) {
        this.id = id;
        this.foodTruck = foodTruck;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FoodTruck getFoodTruck() {
        return foodTruck;
    }

    public void setFoodTruck(FoodTruck foodTruck) {
        this.foodTruck = foodTruck;
    }

    public Set<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(Set<Categorie> categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}