package com.croustify.backend.models.embedded;


import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Address {

    protected String street;

    protected String streetNumber;

    protected String postalCode;

    protected String city;

    protected String province;

    protected String country;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects.equals(streetNumber, address.streetNumber) && Objects.equals(postalCode, address.postalCode) && Objects.equals(city, address.city) && Objects.equals(province, address.province) && Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, streetNumber, postalCode, city, province, country);
    }
}
