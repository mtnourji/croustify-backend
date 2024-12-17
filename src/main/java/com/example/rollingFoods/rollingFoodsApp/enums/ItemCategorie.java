package com.example.rollingFoods.rollingFoodsApp.enums;

public enum ItemCategorie {
    PROMOTION ( "Promotion" ),
    SPECIALITY(    "Speciality" ),
    NEW("New");

    public final String value;

    private ItemCategorie(String value) {
        this.value = value;
    }

}
