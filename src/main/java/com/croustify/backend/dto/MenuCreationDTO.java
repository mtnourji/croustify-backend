package com.croustify.backend.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class MenuCreationDTO {

    private String name;
    private String description;
    private BigDecimal priceTtc;
    private Set<Long> categories = new HashSet<>();

    public MenuCreationDTO() {
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

    public Set<Long> getCategories() {
        return categories;
    }

    public void setCategories(Set<Long> categories) {
        this.categories = categories;
    }

    public BigDecimal getPriceTtc() {
        return priceTtc;
    }

    public void setPriceTtc(BigDecimal priceTtc) {
        this.priceTtc = priceTtc;
    }
}
