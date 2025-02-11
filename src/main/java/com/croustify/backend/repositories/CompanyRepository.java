package com.croustify.backend.repositories;

import com.croustify.backend.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findAllByMembersUserUserCredentialId(long userId);
}
