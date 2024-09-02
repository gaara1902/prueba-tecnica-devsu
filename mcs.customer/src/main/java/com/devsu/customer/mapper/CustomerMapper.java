package com.devsu.customer.mapper;

import com.devsu.customer.dto.CustomerDTO;
import com.devsu.customer.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper
{
	CustomerDTO customerToCustomerDTO(final Customer customer);
	Customer customerDTOToCustomer(final CustomerDTO customerDTO);
}
