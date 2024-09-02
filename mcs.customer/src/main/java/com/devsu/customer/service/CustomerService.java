package com.devsu.customer.service;

import com.devsu.customer.dto.CustomerDTO;

import java.util.List;
import java.util.Map;

public interface CustomerService
{
	List<CustomerDTO> findAll();

	CustomerDTO findByCustomerId(Integer customerId);


	CustomerDTO create(CustomerDTO customerDTO);


	CustomerDTO update(CustomerDTO customerDTO);


	CustomerDTO patch(Integer customerId, Map<String, Object> updates);


	boolean delete(Integer customerId);
}
