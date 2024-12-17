package com.example.rollingFoods.rollingFoodsApp.dto;

import java.util.Set;

public class MenuDTO {

    private Long id;
    private String name;
    private String description;
    private Set<CategorieDTO> categories;

    public MenuDTO() {
    }

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

    public Set<CategorieDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategorieDTO> categories) {
        this.categories = categories;
    }
}
