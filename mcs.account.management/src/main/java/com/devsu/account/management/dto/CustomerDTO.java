package com.devsu.account.management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO
{
	@JsonProperty("customer_id")
	private Integer customerId;

	@NotEmpty(message = "El campo nombre es obligatorio por favor introduzca")
	private String name;

	@NotNull(message = "El campo genero es obligatorio escoja entre MASCULINO, FEMENINO o NO_IDENTIFICA")
	@Enumerated(EnumType.STRING)
	private String gender;

	@Min(18)
	@Max(100)
	private byte age;

	@NotEmpty(message = "El campo identificacion es obligatorio por favor introduzca")
	@Column(unique = true)
	@Pattern(regexp = "^[0-9]{10,13}$",
			message = "Numero de identificación incorrecto, la identificación debe contener entre 10 y 13 digitos")
	private String id;

	@NotEmpty(message = "El campo dirreción es obligatorio, por favor intorduzca")
	private String address;

	@NotEmpty(message = "El campo telefono es obligatorio, por favor intorduzca")
	@Pattern(regexp = "^[0-9]{9,13}$",
			message = "Numero de telefono incorrecto, el telefono debe contener entre 9 y 13 digitos")
	private String phone;

	@NotNull(message = "El campo estado es obligatorio por favor introduzca")
	private Boolean status;

	@NotEmpty(message = "El campo contraseña es obligatorio por favor introduzca")
	private String password;
}
