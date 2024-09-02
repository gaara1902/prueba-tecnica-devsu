package com.devsu.account.management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ReportDTO
{
	@JsonProperty("customer_id")
	private Integer customerId;

	private String name;

	private String id;

	private List<AccountReportDTO> accounts;
}
