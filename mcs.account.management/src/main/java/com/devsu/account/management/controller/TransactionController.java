package com.devsu.account.management.controller;

import com.devsu.account.management.dto.TransactionsDTO;
import com.devsu.account.management.util.BadRequestException;
import com.devsu.account.management.service.TransactionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/movimientos")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionsDTO>> findAll() {
        List<TransactionsDTO> clientes = transactionService.findAll();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionsDTO> findById(@PathVariable final Integer id) {
        return ResponseEntity.ok(transactionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TransactionsDTO> create(@Valid @RequestBody final TransactionsDTO transactionsDTO, BindingResult bindingResult) {
        stringBuilderError(bindingResult);
        TransactionsDTO newTransactionsDTO = transactionService.create(transactionsDTO);
        log.info("Transacción creada con éxito");
        return ResponseEntity.status(HttpStatus.CREATED).body(newTransactionsDTO);
    }

    @PutMapping
    public ResponseEntity<TransactionsDTO> update(@RequestBody final TransactionsDTO transactionsDTO, BindingResult bindingResult) {
        stringBuilderError(bindingResult);
        TransactionsDTO transactionsDTOAcutalizado = transactionService.update(transactionsDTO);
        log.info("Transacción actualizada con éxito");
        return ResponseEntity.status(HttpStatus.OK).body(transactionsDTOAcutalizado);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

        transactionService.delete(id);
    }

    private void stringBuilderError(BindingResult bindingResult) {
        StringBuilder errors = new StringBuilder();
        if (bindingResult.hasErrors()) {
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                errors.append(objectError.getDefaultMessage());
                errors.append("\n");
            }
            throw new BadRequestException(errors.toString());
        }
    }
}
