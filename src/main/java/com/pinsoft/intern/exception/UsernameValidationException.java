package com.pinsoft.intern.exception;

import jakarta.validation.ValidationException;

public class UsernameValidationException extends ValidationException {
    public UsernameValidationException(String message) {
        super(message);
    }
}
