package com.devsu.account.management.api;

import com.devsu.account.management.entity.Customer;

import java.util.concurrent.Future;

public interface CustomerServiceApi
{
	Customer getCustomerById(Integer clienteId);
	Future<Customer> getCustomerAsync(Integer clienteId);
}
