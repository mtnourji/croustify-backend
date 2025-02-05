package com.croustify.backend.controllers;

import com.croustify.backend.dto.LatLng;
import com.croustify.backend.dto.PostalCodeAndLocalityDTO;
import com.croustify.backend.services.GeoLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GeoLocationController {

    @Autowired
    private GeoLocationService geoLocationService;

    @GetMapping("/latLngFromLocation")
    public ResponseEntity<LatLng> getLatLngFromLocation(@RequestParam("locationId") long locationId){
        return ResponseEntity.ok(geoLocationService.getLatLngFromLocation(locationId));
    }

    @GetMapping("/localities")
    public ResponseEntity<List<PostalCodeAndLocalityDTO>> getLocalities(@RequestParam("q") String q){
        return ResponseEntity.ok(geoLocationService.getPostalCodes(q));
    }
}
