package com.croustify.backend.connector;

import com.croustify.backend.dto.LatLng;
import com.croustify.backend.dto.LatLon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Component
public class OpenStreetMapConnector {

    private final WebClient webClient;

    @Autowired
    public OpenStreetMapConnector(WebClient.Builder webClientBuilder) {
        webClient = webClientBuilder.baseUrl("https://nominatim.openstreetmap.org/search").build();
    }

    public LatLon getCoordinates(String postalCode, String street, String streetNumber, String locality) {
        String address = String.format("%s %s, %s, %s", streetNumber, street, locality, postalCode);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("format", "json")
                        .queryParam("q", address)
                        .build())
                .retrieve()
                .bodyToFlux(LatLon.class)
                .next()
                .block(Duration.ofSeconds(10));
    }
}
