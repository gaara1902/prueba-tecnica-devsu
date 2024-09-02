package com.devsu.customer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clientes")
public class Customer extends Person
{
	@NotEmpty(message = "El campo contraseña es obligatorio por favor introduzca")
	@Column(name = "contrasena")
	private String password;

	@NotNull(message = "El campo estado es obligatorio por favor introduzca")
	@Column(name = "estado")
	private Boolean status;
}
