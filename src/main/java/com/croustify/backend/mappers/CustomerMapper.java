package com.croustify.backend.mappers;


import com.croustify.backend.dto.CustomerDTO;
import com.croustify.backend.models.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {


    CustomerDTO customerToDto(Customer customer);
    Customer dtoToCustomer(CustomerDTO dto);
}
