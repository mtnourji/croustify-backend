package com.croustify.backend.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "customer")
public class Customer extends User{

    @OneToMany(mappedBy = "user")
    private List<CompanyMember> companies = new ArrayList<>();

    public List<CompanyMember> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyMember> companies) {
        this.companies = companies;
    }
}
