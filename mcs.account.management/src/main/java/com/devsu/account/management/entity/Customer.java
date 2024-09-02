package com.devsu.account.management.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

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

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	private List<Account> accounts;
}
