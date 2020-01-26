package com.example.customer.service;


import com.example.customer.exception.AppException;
import com.example.customer.exception.NoUniqueCustomerException;
import com.example.customer.exception.ValidationException;
import com.example.customer.mapper.CustomerMapper;
import com.example.customer.model.Customer;
import com.example.customer.persistence.CustomerRepository;
import com.example.customer.view.CustomerView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;


    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }


    @Override
    public CustomerView create(CustomerView customerView) throws AppException {
        if(isValid(customerView)) {
            if(!this.customerRepository.existsByEmail(customerView.getEmail())) {
                try {
                    Customer customer = this.customerMapper.toEntity(customerView);
                    customer.setUuid(UUID.randomUUID().toString());
                    customer = customerRepository.save(customer);
                    return this.customerMapper.toView(customer);
                } catch (Exception ex) {
                    throw new AppException(500, "Unable to save customer", ex);
                }
            } else {
                throw new NoUniqueCustomerException(409, "Customer already exists");
            }
        } else {
            throw new ValidationException(400, "Invalid data received");
        }
    }

    @Override
    public boolean isValid(CustomerView customerView) {
        return customerView != null &&
                customerView.getName() != null &&
                customerView.getEmail() != null &&
                !customerView.getName().trim().equals("") &&
                !customerView.getEmail().trim().equals("");
    }
}
