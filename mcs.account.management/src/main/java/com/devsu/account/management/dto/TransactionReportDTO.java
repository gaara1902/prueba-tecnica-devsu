package com.devsu.account.management.dto;

import com.devsu.account.management.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class TransactionReportDTO
{
	@JsonProperty("transaction_type")
	private TransactionType transactionType;

	private LocalDateTime date;

	private Double value;

	private Double balance;
}
