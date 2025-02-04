package com.croustify.backend.dto;

import java.math.BigDecimal;
import java.util.Set;

public class MenuDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal priceTtc;
    private Set<MenuCategoryDTO> categories;

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

    public Set<MenuCategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<MenuCategoryDTO> categories) {
        this.categories = categories;
    }

    public BigDecimal getPriceTtc() {
        return priceTtc;
    }

    public void setPriceTtc(BigDecimal priceTtc) {
        this.priceTtc = priceTtc;
    }
}
