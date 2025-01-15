package com.croustify.backend.services;

public interface RecaptchaService {

    boolean verifyRecaptcha(String response);
}
