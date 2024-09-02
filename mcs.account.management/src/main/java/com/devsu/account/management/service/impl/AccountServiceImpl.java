package com.devsu.account.management.service.impl;

import com.devsu.account.management.api.CustomerServiceApi;
import com.devsu.account.management.entity.Account;
import com.devsu.account.management.entity.Customer;
import com.devsu.account.management.mapper.AccountMapper;
import com.devsu.account.management.repository.AccountRepository;
import com.devsu.account.management.util.ConflictException;
import com.devsu.account.management.util.NotFoundException;
import com.devsu.account.management.dto.AccountDTO;
import com.devsu.account.management.enums.AccountType;
import com.devsu.account.management.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    public static final String TIPO_CUENTA = "account_type";
    public static final String NUMERO_CUENTA = "account_number";

    private final AccountRepository cuentaRepository;
    private final CustomerServiceApi customerServiceApi;
    private final AccountMapper cuentaMapper;


    @Override
    public List<AccountDTO> findAll() {
        return cuentaRepository.findAll().stream().map(cuentaMapper::accountToAccountDTO).collect(Collectors.toList());
    }

    @Override
    public AccountDTO findById(final Integer id) {
        Optional<Account> cuenta = cuentaRepository.findById(id);
        if (cuenta.isPresent()) {
            return cuenta.map(cuentaMapper::accountToAccountDTO).get();
        } else {
            log.error("Account no encontrada por ID: {}", id);
            throw new NotFoundException(String.format("No se encontró la cuenta con el ID: %d", id));
        }
    }

    @Transactional
    @Override
    public AccountDTO create(final AccountDTO accountDTO) throws ExecutionException, InterruptedException {
        log.info("Inicio de la creación de la account");
        Future<Customer> customerAsync = customerServiceApi.getCustomerAsync(accountDTO.getCustomer().getCustomerId());
        if (findByAccountNumber(accountDTO.getAccountNumber()).isPresent()) {
            log.error("Ya existe una account con el número: {}", accountDTO.getAccountNumber());
            throw new ConflictException(
                    String.format("Ya existe una account con el número: %s", accountDTO.getAccountNumber()));
        }
        Account account = cuentaMapper.accountDTOToAccount(accountDTO);
        Customer customer = customerAsync.get();
        account.setCustomer(customer);
        return cuentaMapper.accountToAccountDTO(cuentaRepository.save(account));
    }

    @Transactional
    @Override
    public AccountDTO updatePatch(final Integer id, final Map<String, Object> results) {
        log.info("Inicio de la actualización parcial de la cuenta");
        Optional<Account> cuenta = cuentaRepository.findById(id);
        if (cuenta.isPresent()) {

            results.forEach((k, v) -> {
                if (k.equals(NUMERO_CUENTA) && !v.equals(cuenta.get().getAccountNumber()) && findByAccountNumber(
                        v.toString()).isPresent()) {
                    log.error("Ya existe una cuenta con el número: {}", v.toString());
                    throw new ConflictException(String.format("Ya existe una cuenta con el número: %d", v.toString()));
                }

                if (k.equals(TIPO_CUENTA)) {
                    v = AccountType.valueOf(v.toString());
                }

                Field field = ReflectionUtils.findField(Account.class, k);
                field.setAccessible(true);
                ReflectionUtils.setField(field, cuenta.get(), v);
            });
            return cuentaMapper.accountToAccountDTO(cuentaRepository.save(cuenta.get()));
        } else {
            log.error("Account no encontrada por ID: {}", id);
            throw new NotFoundException(String.format("No se encontró la cuenta con el ID: %d", id));
        }
    }

    @Transactional
    @Override
    public AccountDTO update(final AccountDTO accountDTO) {
        log.info("Account actualizada con éxito");
        Optional<Account> updateCuenta = cuentaRepository.findById(accountDTO.getId());
        if (updateCuenta.isPresent()) {
            if (!accountDTO.getAccountNumber().equals(updateCuenta.get().getAccountNumber()) &&
                    findByAccountNumber(accountDTO.getAccountNumber()).isEmpty()) {
                log.error("Ya existe una cuenta con el número: {}", accountDTO.getAccountNumber());
                throw new ConflictException(
                        String.format("Ya existe una cuenta con el número: %s", accountDTO.getAccountNumber()));
            }
            Customer customer = customerServiceApi.getCustomerById(accountDTO.getCustomer().getCustomerId());

            final Account account = cuentaMapper.accountDTOToAccount(accountDTO);
            account.setCustomer(customer);
            return cuentaMapper.accountToAccountDTO(cuentaRepository.save(account));
        } else {
            log.error("Account no encontrada por ID: {}", accountDTO.getId());
            throw new NotFoundException(String.format("No se encontró la cuenta con el ID: %d", accountDTO.getId()));
        }
    }

    @Override
    public void delete(final Integer id) {
        cuentaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Account no encontrada por ID: %d", id)));
        cuentaRepository.deleteById(id);
        log.info("Account eliminada con éxito con el ID: {}", id);
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return cuentaRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public List<Account> findAllByCustomer(Customer customerId) {
        return cuentaRepository.findAllByCustomer(customerId);
    }
}
