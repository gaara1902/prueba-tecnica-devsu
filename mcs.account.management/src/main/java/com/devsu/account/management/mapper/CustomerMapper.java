package com.devsu.account.management.mapper;

import com.devsu.account.management.dto.CustomerDTO;
import com.devsu.account.management.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper
{
	Customer customerDTOToCustomer(final CustomerDTO customerDTO);
}
