package com.devsu.account.management.service.impl;

import com.devsu.account.management.dto.TransactionsDTO;
import com.devsu.account.management.entity.Account;
import com.devsu.account.management.entity.Transaction;
import com.devsu.account.management.mapper.TransactionMapper;
import com.devsu.account.management.repository.TransactionRepository;
import com.devsu.account.management.service.AccountService;
import com.devsu.account.management.service.TransactionService;
import com.devsu.account.management.util.BadRequestException;
import com.devsu.account.management.util.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@AllArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService
{
	public static final String SALDO_NO_DISPONIBLE = "Saldo no disponible";
	public static final String CUPO_DIARIO_EXCEDIDO = "Cupo diario Excedido";

	private TransactionRepository transactionRepository;
	private AccountService accountService;
	private TransactionMapper movimientoMapper;

	@Override
	public List<TransactionsDTO> findAll()
	{
		return transactionRepository.findAll().stream().map(movimientoMapper::transactionToTransactionDTO)
				.collect(Collectors.toList());
	}

	@Override
	public TransactionsDTO findById(Integer id)
	{
		Optional<Transaction> movimiento = transactionRepository.findById(id);
		if (movimiento.isPresent())
		{
			return movimiento.map(movimientoMapper::transactionToTransactionDTO).get();
		}
		else
		{
			log.error("Transaction no encontrado por ID: {}", id);
			throw new NotFoundException(String.format("No se encontró el movimiento con el ID: %d", id));
		}
	}

	@Override
	public TransactionsDTO create(TransactionsDTO transactionsDTO)
	{
		log.info("Inicio de la creación del movimiento");
		final Optional<Account> cuenta = accountService.findByAccountNumber(transactionsDTO.getAccount().getAccountNumber());
		if (cuenta.isPresent())
		{
			final double total;
			if (cuenta.get().getTransactions().isEmpty())
			{
				total = createMovimiento(transactionsDTO.getTransactionType().getValue(), cuenta.get().getInitBalance(),
						transactionsDTO.getValue());
			}
			else
			{
				total = createMovimiento(transactionsDTO.getTransactionType().getValue(),
						getUltimoMovimiento(cuenta.get().getTransactions()), transactionsDTO.getValue());
			}
			final Transaction transaction = movimientoMapper.transactionDTOToTransaction(transactionsDTO);
			transaction.setDate(LocalDateTime.now());
			transaction.setAccount(cuenta.get());
			transaction.setBalance(total);
			return movimientoMapper.transactionToTransactionDTO(transactionRepository.save(transaction));
		}
		else
		{
			log.error("Account no encontrada por número: {}", transactionsDTO.getAccount().getAccountNumber());
			throw new NotFoundException(String.format("No se encontró la cuenta con el número: %s",
					transactionsDTO.getAccount().getAccountNumber()));
		}
	}

	private double createMovimiento(final String tipoMovimiento, final double saldo, final double valor)
	{
		double saldoNuevo = saldo;
		switch (tipoMovimiento)
		{
			case "Debito":
				saldoNuevo -= valor;
				break;
			case "Credito":
				saldoNuevo += valor;
				break;
		}

		if (saldoNuevo < 0)
		{
			log.error(SALDO_NO_DISPONIBLE);
			throw new BadRequestException(SALDO_NO_DISPONIBLE);
		}
		return saldoNuevo;
	}

	@Override
	public TransactionsDTO update(final TransactionsDTO transactionsDTO)
	{
		final Optional<Transaction> updateMovimientoOpt = transactionRepository.findById(transactionsDTO.getId());
		if (updateMovimientoOpt.isPresent())
		{
			final Transaction updateTransaction = updateMovimientoOpt.get();
			Optional<Account> cuentaOpt = accountService.findByAccountNumber(transactionsDTO.getAccount().getAccountNumber());
			double saldo = getSaldo(updateTransaction, cuentaOpt);

			updateTransaction.setBalance(
					createMovimiento(transactionsDTO.getTransactionType().getValue(), saldo, transactionsDTO.getValue()));
			updateTransaction.setTransactionType(transactionsDTO.getTransactionType());
			updateTransaction.setValue(transactionsDTO.getValue());

			return movimientoMapper.transactionToTransactionDTO(transactionRepository.save(updateTransaction));
		}
		else
		{
			log.error("Transaction no encontrado por ID: {}", transactionsDTO.getId());
			throw new NotFoundException(String.format("No se encontró el movimiento con el ID: %d", transactionsDTO.getId()));
		}
	}

	@Override
	public void delete(Integer id)
	{
		transactionRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Transaction no encontrado por ID: %d", id)));
		transactionRepository.deleteById(id);
		log.info("Transaction eliminado con éxito con el ID: {}", id);
	}

	private double getSaldo(Transaction updateTransaction, Optional<Account> cuentaOpt)
	{
		double saldo = cuentaOpt.map(cuenta -> {
			double saldoNuevo = getUltimoMovimiento(cuenta.getTransactions());
			if (updateTransaction.getTransactionType().getValue().equals("Debito"))
			{
				saldoNuevo += updateTransaction.getValue();
			}
			else if (updateTransaction.getTransactionType().getValue().equals("Credito"))
			{
				saldoNuevo -= updateTransaction.getValue();
			}
			return saldoNuevo;
		}).orElse(0.0);
		return saldo;
	}

	public double getUltimoMovimiento(List<Transaction> transactions)
	{
		return transactions.isEmpty() ? 0 : transactions.get(transactions.size() - 1).getBalance();
	}

}
