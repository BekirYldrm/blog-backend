package com.pinsoft.intern.exception;

import jakarta.validation.ValidationException;

public class PasswordValidationException extends ValidationException {
    public PasswordValidationException(String message) {
        super(message);
    }
}
