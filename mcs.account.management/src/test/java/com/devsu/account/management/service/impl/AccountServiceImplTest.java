package com.devsu.account.management.service.impl;

import com.devsu.account.management.api.CustomerServiceApi;
import com.devsu.account.management.dto.AccountDTO;
import com.devsu.account.management.dto.CustomerDTO;
import com.devsu.account.management.entity.Account;
import com.devsu.account.management.entity.Customer;
import com.devsu.account.management.enums.AccountType;
import com.devsu.account.management.enums.GenderType;
import com.devsu.account.management.mapper.AccountMapper;
import com.devsu.account.management.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest
{
	@Mock
	private CustomerServiceApi customerServiceApi;
	@Mock
	private AccountMapper accountMapper;
	@Mock
	private AccountRepository accountRepository;
	@InjectMocks
	AccountServiceImpl cuentaService;

	private AccountDTO getMockAccountDto(){
		return AccountDTO.builder()
				.accountType(AccountType.AHORROS)
				.id(1)
				.accountNumber("478758")
				.initBalance(500.0)
				.status(true)
				.customer(getMockCustomerDTO())
				.build();
	}

	private Customer getMockCustomer(){
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

	private CustomerDTO getMockCustomerDTO(){
		return CustomerDTO.builder()
				.name("Jose Lema")
				.customerId(1)
				.id("123456789")
				.phone("098254785")
				.address("Itagui transversal 48")
				.gender("Masculino")
				.age((byte) 20)
				.status(true)
				.password("123456")
				.build();
	}

	private Account getMockAccount(){
		return Account.builder()
				.accountType(AccountType.AHORROS)
				.id(1)
				.accountNumber("478758")
				.initBalance(500.0)
				.status(true)
//				.customerId(1)
				.build();
	}



	@Test
	public void givenCuentaDtoWhenCreateThenExpectCuentaDto() throws ExecutionException, InterruptedException {
		AccountDTO accountDTO = getMockAccountDto();
		Customer customer = getMockCustomer();
		Account account = getMockAccount();
		CompletableFuture<Customer> customerFuture = new CompletableFuture<>();
		customerFuture.complete(customer);

		Mockito.when(customerServiceApi.getCustomerAsync(Mockito.anyInt())).thenReturn(customerFuture);
		Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);
		Mockito.when(accountMapper.accountDTOToAccount(Mockito.any(AccountDTO.class))).thenReturn(account);
		Mockito.when(accountMapper.accountToAccountDTO(Mockito.any(Account.class))).thenReturn(accountDTO);

		AccountDTO result = cuentaService.create(accountDTO);
		Assertions.assertNotNull(result);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(accountDTO.getAccountType(), result.getAccountType());
		Assertions.assertEquals(accountDTO.getId(), result.getId());
		Assertions.assertEquals(accountDTO.getAccountNumber(), result.getAccountNumber());
		Assertions.assertEquals(accountDTO.getInitBalance(), result.getInitBalance());
		Assertions.assertEquals(accountDTO.getStatus(), result.getStatus());

		Mockito.verify(accountRepository).save(Mockito.any(Account.class));
		Mockito.verify(accountMapper).accountDTOToAccount(Mockito.any(AccountDTO.class));
		Mockito.verify(accountMapper).accountToAccountDTO(Mockito.any(Account.class));
	}
}