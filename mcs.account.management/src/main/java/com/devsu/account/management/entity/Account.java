package com.devsu.account.management.entity;

import com.devsu.account.management.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cuentas")
public class Account implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty(message = "El campo Numero de cuenta es obligatorio por favor introduzcalo")
	@Pattern(regexp = "^[0-9]{6,11}$", message = "EL numero de cuenta no es valido, ingrese nuevamente")
	@Column(name = "numero_cuenta")
	private String accountNumber;

	@NotNull(message = "El campo tipo de cuenta es obligatorio, por favor introduzcalo")
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_cuenta")
	private AccountType accountType;

	@NotNull(message = "El campo saldo incial es obligatorio, por favor introduzcalo")
	@Column(name = "saldo_inicial")
	private double initBalance;

	@NotNull(message = "El campo estado es obligatorio, por favor introduzcalo")
	@Column(name = "estado")
	private Boolean status;
//
//	@Column(name = "customer_id")
//	private Integer customerId;

	@ToString.Exclude
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id")
	private Customer customer;

	@ToString.Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
	private List<Transaction> transactions;
}
