package com.devsu.account.management.controller;

import com.devsu.account.management.dto.AccountDTO;
import com.devsu.account.management.service.AccountService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/cuentas")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountDTO>> findAll() throws InterruptedException {
        List<AccountDTO> cuentas = accountService.findAll();
        return new ResponseEntity<>(cuentas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> findById(@PathVariable final Integer id) {
        return ResponseEntity.ok(accountService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AccountDTO> create(@Valid @RequestBody final AccountDTO accountDTO, BindingResult bindingResult)
            throws BadRequestException, ExecutionException, InterruptedException {
        stringBuilderError(bindingResult);
        AccountDTO newcuentaDTO = accountService.create(accountDTO);
        log.info("Account creada con éxito");
        return ResponseEntity.status(HttpStatus.CREATED).body(newcuentaDTO);

    }

    @PutMapping
    public ResponseEntity<AccountDTO> update(@RequestBody final AccountDTO accountDTO, BindingResult bindingResult)
            throws BadRequestException {
        stringBuilderError(bindingResult);
        AccountDTO newcuentaActualizada = accountService.update(accountDTO);
        log.info("Account actualizada con éxito");
        return ResponseEntity.status(HttpStatus.OK).body(newcuentaActualizada);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<AccountDTO> patch(@PathVariable final Integer id, @RequestBody final Map<String, Object> result) {
        AccountDTO cuentaActualizada = accountService.updatePatch(id, result);
        log.info("Account actualizada con éxito");
        return ResponseEntity.status(HttpStatus.OK).body(cuentaActualizada);

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        accountService.delete(id);
    }

    private void stringBuilderError(BindingResult bindingResult) throws BadRequestException {
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
