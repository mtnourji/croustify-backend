package com.croustify.backend.services.imp;

import com.croustify.backend.dto.ContactDTO;
import com.croustify.backend.dto.ContactRequestDTO;
import com.croustify.backend.mappers.ContactMapper;
import com.croustify.backend.models.Contact;
import com.croustify.backend.repositories.ContactRepository;
import com.croustify.backend.services.ContactService;
import com.croustify.backend.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private ContactRepository contactRepo;

    @Autowired
    private EmailService emailService;



    @Override
    public List<ContactDTO> getContacts() {
        final List<Contact> contacts = contactRepo.findAll();
        return contacts.stream().map(contactMapper::contactToDto).toList();
    }

    @Override
    public void deleteContact(Long id) {
        contactRepo.deleteById(id);
    }

    @Override
    public ContactDTO createContact(ContactRequestDTO contactDTO) {
        final Contact newContact = contactMapper.dtoToContact(contactDTO);
        final Contact saved = contactRepo.save(newContact);
        final ContactDTO contactSaved = contactMapper.contactToDto(saved);
        emailService.sendEmailNotificationContact(contactSaved);
        return contactSaved;

    }
}