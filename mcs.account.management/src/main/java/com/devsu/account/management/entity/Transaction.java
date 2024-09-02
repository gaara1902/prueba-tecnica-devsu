package com.devsu.account.management.entity;

import com.devsu.account.management.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movimientos")
public class Transaction
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "fecha")
	private LocalDateTime date;

	@NotNull(message = "El campo tipo de movimiento es obligatorio, seleccione AHORRO o CORRIENTE")
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_movimiento")
	private TransactionType transactionType;

	@NotNull(message = "EL campo valor es obligatorio")
	@Column(name = "valor")
	private Double value;

	@Column(name = "saldo")
	private Double balance;

	@ToString.Exclude
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "numero_cuenta")
	private Account account;
}

