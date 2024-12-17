package com.example.rollingFoods.rollingFoodsApp.models.embedded;


import com.example.rollingFoods.rollingFoodsApp.enums.TauxTva;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class Tva {

    @Enumerated(EnumType.STRING)
    protected TauxTva tauxTva;

    public TauxTva getTauxTva() {
        return tauxTva;
    }

    public void setTauxTva(TauxTva tauxTva) {
        this.tauxTva = tauxTva;
    }
}
