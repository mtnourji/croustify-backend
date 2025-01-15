package com.croustify.backend.models;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.Set;

@Entity
@SQLDelete(sql = "UPDATE category SET active = false WHERE id = ?")
@SQLRestriction("active = true")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @Column(nullable = false)
    private boolean active = true;

    @ManyToMany(mappedBy = "categories")
    private Set<FoodTruck> foodTrucks = new HashSet<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<FoodTruck> getFoodTrucks() {
        return foodTrucks;
    }

    public void setFoodTrucks(Set<FoodTruck> foodTrucks) {
        this.foodTrucks = foodTrucks;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
