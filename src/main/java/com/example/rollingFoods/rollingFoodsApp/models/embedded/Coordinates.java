package com.example.rollingFoods.rollingFoodsApp.models.embedded;


import jakarta.persistence.Embeddable;

@Embeddable
public class Coordinates {

    protected Double latitude;

    protected Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
