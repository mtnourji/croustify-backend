package com.croustify.backend.dto;

public class FavoriteDTO {
    private Long id;
    private Long userCredentialId;
    private Long foodTruckId;

    public FavoriteDTO() {
    }

    public FavoriteDTO(Long id, Long userCredentialId, Long foodTruckId) {
        this.id = id;
        this.userCredentialId = userCredentialId;
        this.foodTruckId = foodTruckId;
    }

    public Long getId() {
        return id;
    }

    public Long getUserCredentialId() {
        return userCredentialId;
    }

    public Long getFoodTruckId() {
        return foodTruckId;
    }
}
