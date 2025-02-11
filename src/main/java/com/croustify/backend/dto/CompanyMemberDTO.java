package com.croustify.backend.dto;

import com.croustify.backend.enums.CompanyMemberRole;

public class CompanyMemberDTO {
    private Long id;
    private CustomerDTO user;

    private CompanyMemberRole role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerDTO getUser() {
        return user;
    }

    public void setUser(CustomerDTO user) {
        this.user = user;
    }

    public CompanyMemberRole getRole() {
        return role;
    }

    public void setRole(CompanyMemberRole role) {
        this.role = role;
    }
}
