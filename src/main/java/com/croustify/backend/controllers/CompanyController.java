package com.croustify.backend.controllers;

import com.croustify.backend.dto.CompanyDTO;
import com.croustify.backend.dto.CompanyDetailsDTO;
import com.croustify.backend.dto.NewCompanyDTO;
import com.croustify.backend.services.CompanyService;
import com.croustify.backend.validation.OwnUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Secured("ROLE_USER")
    @PostMapping("/companies")
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody NewCompanyDTO newCompany){
        final CompanyDTO company = companyService.createCompany(newCompany);
        return ResponseEntity.created(URI.create("/companies/" + company.getId())).body(company);
    }
    @OwnUser
    @Secured("ROLE_USER")
    @GetMapping("/users/{userId}/companies")
    public ResponseEntity<List<CompanyDTO>> getCompanies(@PathVariable("userId") long userId){
        return ResponseEntity.ok(companyService.getUserCompanies(userId));
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/companies/{companyId}")
    public ResponseEntity<CompanyDetailsDTO> getCompany(@PathVariable("companyId") long companyId){
        final CompanyDetailsDTO company = companyService.getCompany(companyId);
        return ResponseEntity.ok(company);
    }
}
