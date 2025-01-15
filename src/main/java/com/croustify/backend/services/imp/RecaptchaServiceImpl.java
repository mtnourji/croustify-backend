package com.croustify.backend.services.imp;


import com.croustify.backend.dto.RecaptchaDTO;
import com.croustify.backend.services.RecaptchaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RecaptchaServiceImpl implements RecaptchaService {

    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    @Value("${recaptcha.secret-key}")
    private String secretKey;

    private final WebClient webClient;

    public RecaptchaServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(RECAPTCHA_VERIFY_URL).build();
    }

    @Override
    public boolean verifyRecaptcha(String token) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("secret", secretKey)
                        .queryParam("response", token)
                        .build())
                .retrieve()
                .bodyToMono(RecaptchaDTO.class)
                .blockOptional()
                .map(RecaptchaDTO::isSuccess)
                .orElse(false);
    }
}
