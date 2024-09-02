package com.devsu.account.management.dto;

import com.devsu.account.management.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO
{
	private Integer id;

	@NotEmpty(message = "El campo numero de cuenta es obligatorio")
	@Pattern(regexp = "^[0-9]{6,11}$", message = "EL numero de cuenta no es valido, ingrese nuevamente")
	@JsonProperty("account_number")
	private String accountNumber;

	@NotNull(message = "El campo tipo de cuenta es obligatorio, por favor introduzcalo")
	@Enumerated(EnumType.STRING)
	@JsonProperty("account_type")
	private AccountType accountType;

	@NotNull(message =  "El campo saldo incial es obligatorio, por favor introduzcalo")
	@JsonProperty("init_balance")
	private Double initBalance;

	@NotNull(message = "El campo estado es obligatorio, por favor introduzcalo")
	private Boolean status;

	@ToString.Exclude
	private CustomerDTO customer;

}
