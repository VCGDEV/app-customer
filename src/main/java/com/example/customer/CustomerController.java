package com.example.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerRepository customerRepository;

    @Value("${application.welcome.message}")
    private String helloMessage;

    private final Logger log = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping
    public HttpEntity<Customer> createCustomer(
            @RequestBody Customer customer
    ){
        log.info(helloMessage);
        this.customerRepository.saveAndFlush(customer);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping
    public HttpEntity<List<Customer>> findAll() {
        log.info(helloMessage);
        return new ResponseEntity<>(this.customerRepository.findAll(),
                HttpStatus.OK);
    }
}
