package com.devsu.account.management.mapper;

import com.devsu.account.management.dto.AccountDTO;
import com.devsu.account.management.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper
{
	AccountDTO accountToAccountDTO(final Account account);
	Account accountDTOToAccount(final AccountDTO accountDTO);
}
