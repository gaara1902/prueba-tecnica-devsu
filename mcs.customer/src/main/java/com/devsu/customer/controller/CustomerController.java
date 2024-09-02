package com.devsu.customer.controller;


import com.devsu.customer.dto.CustomerDTO;
import com.devsu.customer.service.CustomerService;
import com.devsu.customer.util.BadRequestException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/clientes")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> findAll() {

		return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> findByCustomerId(@PathVariable Integer customerId) {

        return ResponseEntity.ok(customerService.findByCustomerId(customerId));
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> create(@Valid @RequestBody CustomerDTO customerDTO, BindingResult bindingResult) {

		validateBindingResult(bindingResult);
        CustomerDTO newClientDTO = customerService.create(customerDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(newClientDTO);
    }

    @PutMapping
    public ResponseEntity<CustomerDTO> update(@Valid @RequestBody CustomerDTO customerDTO, BindingResult bindingResult) {

		validateBindingResult(bindingResult);
        CustomerDTO clientUpdated = customerService.update(customerDTO);

        return ResponseEntity.ok(clientUpdated);
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> updatePatch(@PathVariable Integer customerId, @RequestBody Map<String, Object> results) {
        CustomerDTO clientUpdated = customerService.patch(customerId, results);
        return ResponseEntity.ok(clientUpdated);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer customerId) {
        customerService.delete(customerId);
    }

    private void validateBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for(ObjectError objectError: bindingResult.getAllErrors()){
                errors.append(objectError.getDefaultMessage()).append("\n");
            }
            throw new BadRequestException(errors.toString());
        }
    }
}
