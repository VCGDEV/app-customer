package com.example.customer.mapper;

import com.example.customer.model.Customer;
import com.example.customer.view.CustomerView;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerView customerView) {
        if(customerView == null) {
            throw new IllegalArgumentException("The customer should not be null");
        }
        Customer customer = new Customer();
        customer.setEmail(customerView.getEmail());
        customer.setName(customerView.getName());
        return customer;
    }

    public CustomerView toView(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("The customer should no be null");
        }
        CustomerView view = new CustomerView();
        view.setEmail(customer.getEmail());
        view.setId(customer.getUuid());
        view.setName(customer.getName());
        return view;
    }
}
