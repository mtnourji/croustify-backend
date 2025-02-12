package com.croustify.backend.repositories;

import com.croustify.backend.models.Company;
import com.croustify.backend.models.CompanyInvitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyInvitationRepository extends JpaRepository<CompanyInvitation, Long> {
    boolean existsByTargetEmailAndCompanyId(String email, Company company);
}
