package com.croustify.backend.enums;



public enum PayementType {

    CASH("Cash"),
    CREDIT_CARD("Credit Card");

    public final String value;

    private PayementType(String value) {
        this.value = value;
    }

}
