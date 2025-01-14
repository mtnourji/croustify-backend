package com.croustify.backend.enums;

public enum FoodType {
    BURGER("Burger"),
    PIZZA("Pizza"),
    FRIES("Frites"),
    CHICKEN("Chicken"),
    HOTDOG("Hotdog"),
    CHINESE("Chinese"),
    SUSHI("Sushi"),
    PITTA("Pitta"),
    DURUM("Durum"),
    GLACES("Glaces");




    public final String value;

    private FoodType(String value) {
        this.value = value;
    }


}
