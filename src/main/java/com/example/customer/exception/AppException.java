package com.example.customer.exception;

public class AppException extends Exception {
    private Integer code;
    private String message;

    public AppException(Integer code, String message, Throwable cause) {
        super(cause);
        this.code = code;
        this.message = message;
    }


    public AppException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
