package com.pinsoft.intern.exception;

import jakarta.validation.ValidationException;

public class EmailValidationException extends ValidationException {
    public EmailValidationException(String message) {
        super(message);
    }
}
