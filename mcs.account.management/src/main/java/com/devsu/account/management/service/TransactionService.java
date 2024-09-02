package com.devsu.account.management.service;

import com.devsu.account.management.dto.TransactionsDTO;

import java.util.List;

public interface TransactionService
{
	List<TransactionsDTO> findAll();

	TransactionsDTO findById(final Integer id);

	TransactionsDTO create(final TransactionsDTO transactionsDTO);

	TransactionsDTO update(final TransactionsDTO transactionsDTO);

	void delete(final Integer id);
}
