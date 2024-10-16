package com.pinsoft.intern.exception;

import jakarta.validation.ValidationException;

public class NameValidationException extends ValidationException {
    public NameValidationException(String message) {
        super(message);
    }
}
