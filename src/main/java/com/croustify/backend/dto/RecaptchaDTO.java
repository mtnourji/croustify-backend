package com.croustify.backend.dto;


public class RecaptchaDTO {

    private final boolean success;
    private final float score;

    public RecaptchaDTO(boolean success, float score) {
        this.success = success;
        this.score = score;
    }

    public boolean isSuccess() {
        return success;
    }

    public float getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "RecaptchaDTO{" +
                "success=" + success +
                ", score=" + score +
                '}';
    }
}
