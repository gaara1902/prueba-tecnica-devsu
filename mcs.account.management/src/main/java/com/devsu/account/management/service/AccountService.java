package com.devsu.account.management.service;

import com.devsu.account.management.dto.AccountDTO;
import com.devsu.account.management.entity.Account;
import com.devsu.account.management.entity.Customer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface AccountService
{
	List<AccountDTO> findAll();

	AccountDTO findById(final Integer id);

	AccountDTO create(final AccountDTO accountDTO) throws ExecutionException, InterruptedException;

	AccountDTO update(final AccountDTO accountDTO);

	AccountDTO updatePatch(final Integer id, final Map<String, Object> results);

	void delete(final Integer id);

	Optional<Account> findByAccountNumber(final String numeroCuenta);

	List<Account> findAllByCustomer(Customer customer);
}
