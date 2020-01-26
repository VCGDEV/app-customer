package com.example.customer.service;

import com.example.customer.exception.AppException;
import com.example.customer.view.CustomerView;

public interface CustomerService {
    CustomerView create(CustomerView customerView) throws AppException;
    boolean isValid(CustomerView customerView);
}
