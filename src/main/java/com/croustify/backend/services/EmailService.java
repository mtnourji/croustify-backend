package com.croustify.backend.services;

import com.croustify.backend.dto.ContactDTO;
import com.croustify.backend.models.Company;

public interface EmailService {
    void sendEmailNotificationContact(ContactDTO contactDTO);

    void sendCompanyInvitation(Company company, String email);

    void sendPasswordResetToken(String email, String token);
}
