package com.croustify.backend.services;

import com.croustify.backend.dto.CompanyDTO;
import com.croustify.backend.dto.CompanyDetailsDTO;
import com.croustify.backend.dto.NewCompanyDTO;
import com.croustify.backend.enums.CompanyMemberRole;
import com.croustify.backend.mappers.CompanyMapper;
import com.croustify.backend.models.Company;
import com.croustify.backend.models.CompanyMember;
import com.croustify.backend.models.Customer;
import com.croustify.backend.repositories.CompanyRepository;
import com.croustify.backend.repositories.CustomerRepo;
import com.croustify.backend.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private CustomerRepo customerRepo;

    @Transactional
    public CompanyDTO createCompany(NewCompanyDTO newCompany) {
        final Company company = companyMapper.toEntity(newCompany);
        final CompanyMember companyMember = new CompanyMember();
        final Customer customer = customerRepo.getReferenceByUserCredentialId(SecurityUtil.getConnectedUserOrThrow().getId());
        companyMember.setCompany(company);
        companyMember.setUser(customer);
        companyMember.setRole(CompanyMemberRole.ADMIN);
        company.setCreatedBy(customer);
        companyMember.setCreatedBy(customer);
        company.getMembers().add(companyMember);
        final Company saved = companyRepository.save(company);
        return companyMapper.toDTO(saved);
    }

    @Transactional
    public CompanyDetailsDTO getCompany(long companyId) {
        final Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company " + companyId + " does not exist"));
        if(company.getMembers().stream().noneMatch(member -> member.getUser().getUserCredential().getId().equals(SecurityUtil.getConnectedUserOrThrow().getId()))){
            throw new AuthorizationDeniedException("You are not authorized to access company " + companyId, new AuthorizationDecision(false));
        }
        return companyMapper.toDetailDTO(company);
    }

    public List<CompanyDTO> getUserCompanies(long userId) {
        List<Company> companies = companyRepository.findAllByMembersUserUserCredentialId(userId);
        return companyMapper.toDTO(companies);
    }
}
