package com.devsu.customer.service.impl;

import com.devsu.customer.dto.CustomerDTO;
import com.devsu.customer.entity.Customer;
import com.devsu.customer.enums.GenderType;
import com.devsu.customer.mapper.CustomerMapper;
import com.devsu.customer.repository.CustomerRepository;
import com.devsu.customer.service.CustomerService;
import com.devsu.customer.util.ConflictException;
import com.devsu.customer.util.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Transactional
@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private static final String PASSWORD = "password";
    private static final String AGE = "age";
    private static final String ID = "id";

    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> findAll() {
        List<CustomerDTO> list = new ArrayList<>();
        for (Customer customer : customerRepository.findAll()) {
            CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
            list.add(customerDTO);
        }
        return list;
    }

    @Override
    public CustomerDTO findByCustomerId(final Integer clientId) {
        Optional<Customer> client = customerRepository.findByCustomerId(clientId);
        if (client.isPresent()) {
            return customerMapper.customerToCustomerDTO(client.get());
        } else {
            log.error("Customer no encontrado con el ID: {}", clientId);
            throw new NotFoundException(String.format("No se pudo encontrar un customer con el ID: %d", clientId));
        }
    }

    @Override
    public CustomerDTO create(final CustomerDTO customerDTO) {
        log.info("Inicio de la creación del customer");
        if (getId(customerDTO.getId()).isPresent()) {
            log.error("Ya existe un customer con la identificación: {}", customerDTO.getId());
            throw new ConflictException(
                    String.format("Ya existe un customer con la identificación: %s", customerDTO.getId()));
        }
        customerDTO.setPassword(encryptPassword(customerDTO.getPassword()));
        return customerMapper.customerToCustomerDTO(
                customerRepository.save(customerMapper.customerDTOToCustomer(customerDTO)));
    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO) {
        log.info("Inicio de la actualización del customer");
        Optional<Customer> updateClient = customerRepository.findByCustomerId(customerDTO.getCustomerId());
        if (updateClient.isPresent()) {
            if (!customerDTO.getId().equals(updateClient.get().getId()) &&
                    getId(customerDTO.getId()).isPresent()) {
                log.error("Ya existe un customer con la identificación: {}", customerDTO.getId());
                throw new ConflictException(
                        String.format("Ya existe un customer con la identificación: %s", customerDTO.getId()));
            }
            customerDTO.setPassword(encryptPassword(customerDTO.getPassword()));
            return customerMapper.customerToCustomerDTO(
                    customerRepository.save(customerMapper.customerDTOToCustomer(customerDTO)));
        } else {
            log.error("Customer no encontrado con el ID: {}", customerDTO.getCustomerId());
            throw new NotFoundException(String.format("No se pudo encontrar un customer con el ID: %d", customerDTO.getCustomerId()));
        }
    }

    @Override
    public CustomerDTO patch(Integer customerId, Map<String, Object> updates) {
        Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
        if (customer.isPresent()) {
            for (Map.Entry<String, Object> entry : updates.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (key.equals(ID) && !value.equals(customer.get().getId()) && getId(value.toString()).isPresent()) {
                    log.error("Ya existe un customer con la identificación: {}", value);
                    throw new ConflictException(String.format("Ya existe un customer con la identificación: %s", value));
                }
                if (key.equals(AGE)) {
                    value = Byte.parseByte(value.toString());
                }
                if (key.equals(PASSWORD)) {
                    value = encryptPassword(value.toString());
                }
                Field field = ReflectionUtils.findField(Customer.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, customer.get(), value);
            }
            return customerMapper.customerToCustomerDTO(customerRepository.save(customer.get()));
        } else {
            log.error("Customer no encontrado con el ID: {}", customerId);
            throw new NotFoundException(String.format("No se pudo encontrar un customer con el ID: %d", customerId));
        }
    }

    @Override
    public boolean delete(Integer clientId) {
        if (customerRepository.findByCustomerId(clientId).isEmpty()) {
            throw new NotFoundException(String.format("No se pudo encontrar un customer con el ID: %d", clientId));
        }
        customerRepository.deleteByCustomerId(clientId);
        log.info("Customer eliminado con éxito con el ID: {}", clientId);

        return true;
    }

    public Optional<Customer> getId(String id) {
        return customerRepository.findById(id);
    }

    private String encryptPassword(String password) {
        return String.valueOf(password.hashCode());
    }

}
