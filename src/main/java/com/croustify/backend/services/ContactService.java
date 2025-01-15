package com.croustify.backend.services;

import com.croustify.backend.dto.ContactDTO;
import com.croustify.backend.dto.ContactRequestDTO;

import java.util.List;

public interface ContactService {

    List<ContactDTO> getContacts();
    void deleteContact(Long id);
    ContactDTO createContact(ContactRequestDTO contactDTO);
}