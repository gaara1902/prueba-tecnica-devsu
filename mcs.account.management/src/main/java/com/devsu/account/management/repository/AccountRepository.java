package com.devsu.account.management.repository;

import com.devsu.account.management.entity.Account;
import com.devsu.account.management.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>
{
	Optional<Account> findByAccountNumber(final String accountNumber);

	List<Account> findAllByCustomer(Customer customer);
}
