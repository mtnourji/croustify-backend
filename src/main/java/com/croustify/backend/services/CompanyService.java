package com.croustify.backend.services;

import com.croustify.backend.dto.CompanyDTO;
import com.croustify.backend.dto.CompanyDetailsDTO;
import com.croustify.backend.dto.CompanyInvitationRequestDTO;
import com.croustify.backend.dto.NewCompanyDTO;
import com.croustify.backend.enums.CompanyMemberRole;
import com.croustify.backend.mappers.CompanyMapper;
import com.croustify.backend.models.*;
import com.croustify.backend.repositories.CompanyInvitationRepository;
import com.croustify.backend.repositories.CompanyRepository;
import com.croustify.backend.repositories.CustomerRepo;
import com.croustify.backend.repositories.UserCredentialRepo;
import com.croustify.backend.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyService {
    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);
    @Autowired
    private UserCredentialRepo userCredentialRepo;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyInvitationRepository companyInvitationRepository;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private EmailService emailService;

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

    @Transactional
    public void inviteToCompany(long companyId, CompanyInvitationRequestDTO request) {
        final UserCredential requestor = userCredentialRepo.getReferenceById(SecurityUtil.getConnectedUserOrThrow().getId());
        final Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company " + companyId + " does not exist"));
        if(company.getMembers().stream().noneMatch(member ->
                member.getUser().getUserCredential().getId().equals(SecurityUtil.getConnectedUserOrThrow().getId())
                && CompanyMemberRole.ADMIN.equals(member.getRole()))){
            throw new AuthorizationDeniedException("You are not authorized to access company " + companyId, new AuthorizationDecision(false));
        }
        if(companyInvitationRepository.existsByTargetEmailAndCompanyId(request.getEmail(), company)){
            logger.warn("Invitaiton to {} already exists for company {}", request.getEmail(), companyId);
            throw new IllegalArgumentException("The invitation has already been sent to this user");
        }
        final CompanyInvitation companyInvitation = new CompanyInvitation();
        companyInvitation.setCompany(company);
        companyInvitation.setDeeplink(UUID.randomUUID().toString());
        companyInvitation.setRequestor(requestor);
        companyInvitation.setTargetEmail(request.getEmail());
        companyInvitationRepository.save(companyInvitation);
        emailService.sendCompanyInvitation(company, request.getEmail());
    }
}
