package com.devsu.account.management.api.impl;

import com.devsu.account.management.entity.Customer;
import com.devsu.account.management.util.NotFoundException;
import com.devsu.account.management.api.CustomerServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@Service
public class CustomerServiceApiImpl implements CustomerServiceApi
{

	private RestTemplate restTemplate;
	private String customerServiceUrl;


	@Autowired
	public CustomerServiceApiImpl(RestTemplate restTemplate, @Value("${external.service.url}") String customerServiceUrl) {
		this.restTemplate = restTemplate;
		this.customerServiceUrl = customerServiceUrl;
	}

	@Override
	public Customer getCustomerById(Integer customerId)
	{
		String url = String.format("%s/%s",
				customerServiceUrl, customerId);
		ResponseEntity<Customer> response = restTemplate.getForEntity(url, Customer.class);

		if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
			log.error("No se pudo encontrar un customere con el ID: {}", customerId);
			throw new NotFoundException(String.format("No se pudo encontrar un customere con el ID: %d", customerId));
		}
		return response.getBody();
	}

	@Async
	@Override
	public Future<Customer> getCustomerAsync(Integer customerId) {

		final CompletableFuture<Customer> completableFuture  = new CompletableFuture<>();
		Executors.newCachedThreadPool().submit(() -> {
		try {
			completableFuture.complete(getCustomerById(customerId));
		}catch (RuntimeException e){
			completableFuture.completeExceptionally(e);
		}
		});
		return completableFuture;
	}
}
