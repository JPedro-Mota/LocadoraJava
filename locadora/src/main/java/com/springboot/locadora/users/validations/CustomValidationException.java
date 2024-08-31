package com.springboot.locadora.users.validations;

public class CustomValidationException extends RuntimeException {

    public CustomValidationException(String message) {
        super(message);
    }
}
