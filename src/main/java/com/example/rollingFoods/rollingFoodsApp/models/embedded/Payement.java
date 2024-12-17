package com.example.rollingFoods.rollingFoodsApp.models.embedded;


import com.example.rollingFoods.rollingFoodsApp.enums.PayementType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class Payement {

    @Enumerated(EnumType.STRING)
    protected PayementType payementType;

    public PayementType getPayementType() {
        return payementType;
    }

    public void setPayementType(PayementType payementType) {
        this.payementType = payementType;
    }
}
