package com.croustify.backend.controllers;

import com.croustify.backend.dto.ContactDTO;
import com.croustify.backend.dto.ContactRequestDTO;
import com.croustify.backend.services.ContactService;
import com.croustify.backend.services.RecaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContactController {

    private static final Logger logger =  LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private RecaptchaService recaptchaService;

    @GetMapping("/contacts")
    public ResponseEntity<List<ContactDTO>> contacts() {
        logger.info("Getting all contacts");
        List<ContactDTO> contacts = contactService.getContacts();
        return ResponseEntity.ok(contacts);
    }

    @PostMapping("/contacts")
    public ResponseEntity<ContactDTO> contact(@RequestBody ContactRequestDTO contactDTO) {
        if(!recaptchaService.verifyRecaptcha(contactDTO.getToken())) {
            return ResponseEntity.badRequest().build();
        }
        ContactDTO createContact = contactService.createContact(contactDTO);
        return ResponseEntity.ok(createContact);
    }
}