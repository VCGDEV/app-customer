package com.example.customer.controller;

import com.example.customer.exception.AppException;
import com.example.customer.service.CustomerService;
import com.example.customer.view.CustomerView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;

    private final Logger log = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public HttpEntity<CustomerView> createCustomer(
            @RequestBody CustomerView customer
    ) throws AppException {
        log.info("Creating new customer");
        customer = this.customerService.create(customer);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

}
