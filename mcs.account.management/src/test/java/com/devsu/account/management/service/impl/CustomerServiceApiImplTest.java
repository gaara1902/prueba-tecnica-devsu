package com.devsu.account.management.service.impl;

import com.devsu.account.management.api.CustomerServiceApi;
import com.devsu.account.management.entity.Customer;
import com.devsu.account.management.enums.GenderType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CustomerServiceApiImplTest
{
	@Autowired
	private CustomerServiceApi customerServiceApi;

	@MockBean
	private RestTemplate restTemplate;

	private Customer getMockCliente(){
		return Customer.builder()
				.name("Jose Lema")
				.customerId(1)
				.id("123456789")
				.phone("098254785")
				.address("Itagui transversal 48")
				.genderType(GenderType.MASCULINO)
				.age((byte) 20)
				.status(true)
				.password("123456")
				.build();
	}

	@Test
	void whenGetClienteById_thenClienteShouldBeReturned() {
		Customer customerEsperado = getMockCliente();
		ResponseEntity<Customer> responseEntity = ResponseEntity.ok(customerEsperado);
		Mockito.when(restTemplate.getForEntity(ArgumentMatchers.anyString(), ArgumentMatchers.eq(Customer.class))).thenReturn(responseEntity);

		Customer customer = customerServiceApi.getCustomerById(1);

		assertNotNull(customer);
		assertEquals(customerEsperado.getCustomerId(), customer.getCustomerId());
		assertEquals(customerEsperado.getName(), customer.getName());
		assertEquals(customerEsperado.getId(), customer.getId());
		assertEquals(customerEsperado.getPhone(), customer.getPhone());
		assertEquals(customerEsperado.getAddress(), customer.getAddress());
		assertEquals(customerEsperado.getGenderType(), customer.getGenderType());
		assertEquals(customerEsperado.getAge(), customer.getAge());
		assertEquals(customerEsperado.getStatus(), customer.getStatus());
		assertEquals(customerEsperado.getPassword(), customer.getPassword());

		Mockito.verify(restTemplate).getForEntity(ArgumentMatchers.anyString(), ArgumentMatchers.eq(Customer.class));
	}
}