package com.devsu.account.management.service.impl;

import com.devsu.account.management.dto.ReportDTO;
import com.devsu.account.management.dto.AccountReportDTO;
import com.devsu.account.management.dto.TransactionReportDTO;
import com.devsu.account.management.entity.Customer;
import com.devsu.account.management.entity.Account;
import com.devsu.account.management.entity.Transaction;
import com.devsu.account.management.api.CustomerServiceApi;
import com.devsu.account.management.service.AccountService;
import com.devsu.account.management.service.ReportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    private CustomerServiceApi customerServiceApi;
    private AccountService accountService;

    @Override
    public ReportDTO getReport(Integer customerId, LocalDate startingDate, LocalDate endDate) {
        Customer customer = customerServiceApi.getCustomerById(customerId);
        log.info(String.format("Respuesta de la llamada al mcs.customer %s", customer.getCustomerId()));
        customer.setAccounts(getAccountsByCustomer(customer, startingDate, endDate));
        return ReportDTO.builder().customerId(customer.getCustomerId()).name(customer.getName())
                .id(customer.getId()).accounts(getAccounts(customer.getAccounts())).build();

    }

    private List<AccountReportDTO> getAccounts(List<Account> accounts) {
        List<AccountReportDTO> collect = accounts.stream().map(this::getAccountReportDTO).collect(Collectors.toList());

        return collect;
    }

    private AccountReportDTO getAccountReportDTO(Account account) {
        double totalDebits = account.getTransactions().stream().filter(movimiento -> "Debito".equals(movimiento.getTransactionType().getValue())).mapToDouble(Transaction::getValue).sum();

        double totalCredits = account.getTransactions().stream().filter(m -> "Credito".equals(m.getTransactionType().getValue())).mapToDouble(Transaction::getValue).sum();
        return AccountReportDTO.builder().accountNumber(account.getAccountNumber()).accountType(account.getAccountType().getValue())
                .transactions(getTransactions(account.getTransactions())).totalDebits(totalDebits).totalCredits(totalCredits)
                .initialBalance(account.getInitBalance()).balance(account.getInitBalance() + (totalCredits - totalDebits))
                .build();
    }

    private List<TransactionReportDTO> getTransactions(List<Transaction> transactions) {
        List<TransactionReportDTO> list = transactions.stream().map(movimiento -> TransactionReportDTO.builder().transactionType(movimiento.getTransactionType()).date(movimiento.getDate())
                .value(movimiento.getValue()).balance(movimiento.getBalance()).build()).collect(Collectors.toList());
        return list;
    }

    private List<Account> getAccountsByCustomer(Customer customer, LocalDate fechaInicial, LocalDate fechaFinal) {
        List<Account> accounts = accountService.findAllByCustomer(customer);
        log.info(String.format("Buscando cuentas by customer %s", accounts));
        List<Account> list = new ArrayList<>();
        for (Account account : accounts) {
            log.info(String.format("Se recorre la cuenta: %s", account));
            List<Transaction> movimientosFiltrados = new ArrayList<>();
            log.info(String.format("Lista de transacciones: %s", account.getTransactions()));
            for (Transaction transaction : account.getTransactions()) {
                log.info(String.format("Se recorre transacciones: %s", transaction));
                if (!transaction.getDate().toLocalDate().isBefore(fechaInicial) && !transaction.getDate()
                        .toLocalDate().isAfter(fechaFinal)) {
                    movimientosFiltrados.add(transaction);
                }
            }
            account.setTransactions(movimientosFiltrados);
            list.add(account);
        }
        return list;
    }
}
