package com.devsu.account.management.dto;

import com.devsu.account.management.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionsDTO
{
	private Integer id;

	private LocalDateTime date;

	@NotNull(message = "El campo tipo de movimiento es obligatorio, seleccione AHORRO o CORRIENTE")
	@Enumerated(EnumType.STRING)
	@JsonProperty("transaction_type")
	private TransactionType transactionType;

	@NotNull(message = "EL campo valor es obligatorio")
	private Double value;

	private Double balance;

	private AccountDTO account;
}