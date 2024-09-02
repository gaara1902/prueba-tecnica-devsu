package com.devsu.customer.repository;

import com.devsu.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>
{
	Optional<Customer> findById(final String id);
	Optional<Customer> findByCustomerId(final Integer customerId);
	void deleteByCustomerId(final Integer clienteId);
}
