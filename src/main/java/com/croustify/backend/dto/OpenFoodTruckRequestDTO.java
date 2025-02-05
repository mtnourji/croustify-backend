package com.croustify.backend.dto;

public class OpenFoodTruckRequestDTO {

    private OpenFoodTruckMode openFoodTruckMode;
    private AddressDTO customAddress;
    private LatLng latLng;

    public OpenFoodTruckMode getOpenFoodTruckMode() {
        return openFoodTruckMode;
    }

    public void setOpenFoodTruckMode(OpenFoodTruckMode openFoodTruckMode) {
        this.openFoodTruckMode = openFoodTruckMode;
    }

    public AddressDTO getCustomAddress() {
        return customAddress;
    }

    public void setCustomAddress(AddressDTO customAddress) {
        this.customAddress = customAddress;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
