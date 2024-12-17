package com.example.rollingFoods.rollingFoodsApp.mappers;


import com.example.rollingFoods.rollingFoodsApp.dto.CustomerDTO;
import com.example.rollingFoods.rollingFoodsApp.models.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {


    CustomerDTO customerToDto(Customer customer);
    Customer dtoToCustomer(CustomerDTO dto);
}
