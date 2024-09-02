package com.devsu.account.management.mapper;

import com.devsu.account.management.dto.TransactionsDTO;
import com.devsu.account.management.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper
{
	TransactionsDTO transactionToTransactionDTO(final Transaction transaction);
	Transaction transactionDTOToTransaction(final TransactionsDTO transactionsDTO);
}
