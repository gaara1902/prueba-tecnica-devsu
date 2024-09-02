package com.devsu.customer.service.impl;

import com.devsu.customer.dto.CustomerDTO;
import com.devsu.customer.entity.Customer;
import com.devsu.customer.enums.GenderType;
import com.devsu.customer.mapper.CustomerMapper;
import com.devsu.customer.repository.CustomerRepository;
import com.devsu.customer.util.ConflictException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest
{
	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private CustomerMapper customerMapper;

	@InjectMocks
	private CustomerServiceImpl customerService;

	@Test
	void whenCreateCalledWithNewCustomer_thenCustomerShouldBeCreated() {
		CustomerDTO customerDTO = getMockCustomerDTO();
		Customer customer = getMockCustomer();

		Mockito.when(customerRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

		Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);
		Mockito.when(customerMapper.customerToCustomerDTO(Mockito.any(Customer.class))).thenReturn(customerDTO);
		Mockito.when(customerMapper.customerDTOToCustomer(Mockito.any(CustomerDTO.class))).thenReturn(customer);

		CustomerDTO result = customerService.create(customerDTO);
		Assertions.assertNotNull(result);
	}

	@Test
	void whenCreateCalledWithExistingCustomer_thenConflictExceptionShouldBeThrown() {
		CustomerDTO customerDTO = getMockCustomerDTO();
		Customer customer = getMockCustomer();

		Mockito.when(customerRepository.findById(Mockito.anyString())).thenReturn(Optional.of(customer));

		Assertions.assertThrows(ConflictException.class, () -> customerService.create(customerDTO));

		Mockito.verify(customerRepository, Mockito.never()).save(Mockito.any(Customer.class));
	}

	private Customer getMockCustomer(){
		return Customer.builder()
				.name("Jose Lema")
				.customerId(1)
				.id("123456789")
				.phone("098254785")
				.address("Itagui transversal 48")
				.gender(GenderType.MASCULINO.getValue())
				.age((byte) 20)
				.status(true)
				.password("123456")
				.build();
	}

	private CustomerDTO getMockCustomerDTO(){
		return CustomerDTO.builder()
				.name("Jose Lema")
				.customerId(1)
				.id("123456789")
				.phone("098254785")
				.address("Itagui transversal 48")
				.gender(GenderType.MASCULINO)
				.age((byte) 20)
				.status(true)
				.password("123456")
				.build();
	}
}