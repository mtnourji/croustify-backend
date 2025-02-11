package com.croustify.backend.mappers;


import com.croustify.backend.dto.CompanyDTO;
import com.croustify.backend.dto.CompanyDetailsDTO;
import com.croustify.backend.dto.CustomerDTO;
import com.croustify.backend.dto.NewCompanyDTO;
import com.croustify.backend.models.Company;
import com.croustify.backend.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompanyMapper {

    Company toEntity(NewCompanyDTO newCompany);

    CompanyDTO toDTO(Company saved);

    CompanyDetailsDTO toDetailDTO(Company company);
    @Mapping(target = "email", source = "userCredential.email")
    CustomerDTO map(User value);

    List<CompanyDTO> toDTO(List<Company> companies);
}
