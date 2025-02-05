package com.croustify.backend.services;

import com.croustify.backend.dto.LatLng;
import com.croustify.backend.dto.PostalCodeAndLocalityDTO;
import com.croustify.backend.models.PostalCode;
import com.croustify.backend.repositories.PostalCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeoLocationService {

    @Autowired
    private PostalCodeRepository postalCodeRepository;

    public LatLng getLatLngFromLocation(long locationId) {
        final PostalCode postalCode = postalCodeRepository.findById(locationId).orElseThrow(() -> new IllegalArgumentException("location " + locationId + " not found"));
        final LatLng latLng = new LatLng();
        latLng.setLat(postalCode.getLatitude());
        latLng.setLng(postalCode.getLongitude());
        return latLng;
    }

    public List<PostalCodeAndLocalityDTO> getPostalCodes(final String query){
        List<PostalCode> matches = postalCodeRepository.findAllByPostalCodeContainingOrLocalityContaining(query, query);
        return matches.stream().map(postalCode -> {
            final PostalCodeAndLocalityDTO dto = new PostalCodeAndLocalityDTO();
            dto.setId(postalCode.getPostalcodecountryKey());
            dto.setPostalCode(postalCode.getPostalCode());
            dto.setLocality(postalCode.getLocality());
            return dto;
        }).toList();
    }
}
