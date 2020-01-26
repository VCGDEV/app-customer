package com.example.customer.exception;

public class ValidationException extends AppException {
    public ValidationException(Integer code, String message) {
        super(code, message);
    }
}
