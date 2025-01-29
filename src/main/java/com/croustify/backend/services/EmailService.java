package com.croustify.backend.services;

import com.croustify.backend.dto.ContactDTO;

public interface EmailService {
    void sendEmailNotificationContact(ContactDTO contactDTO);
}
