package com.example.customer.exception;

public class NoUniqueCustomerException extends AppException {
    public NoUniqueCustomerException(Integer code, String message) {
        super(code, message);
    }
}
