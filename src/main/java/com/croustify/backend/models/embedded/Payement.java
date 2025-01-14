package com.croustify.backend.models.embedded;


import com.croustify.backend.enums.PayementType;
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
