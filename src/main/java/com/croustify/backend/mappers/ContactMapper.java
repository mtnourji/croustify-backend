package com.croustify.backend.mappers;

import com.croustify.backend.dto.ContactDTO;
import com.croustify.backend.dto.ContactRequestDTO;
import com.croustify.backend.models.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContactMapper {

    Contact dtoToContact(ContactRequestDTO dto);
    ContactDTO contactToDto(Contact contact);
}
