package com.croustify.backend.dto;

import java.util.ArrayList;
import java.util.List;

public class CompanyDetailsDTO {
    private Long id;

    private String name;

    private AddressDTO address;

    private String vatNumber;
    private List<CompanyMemberDTO> members = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public List<CompanyMemberDTO> getMembers() {
        return members;
    }

    public void setMembers(List<CompanyMemberDTO> members) {
        this.members = members;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
}
