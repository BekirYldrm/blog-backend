package com.pinsoft.intern.exception;

import jakarta.validation.ValidationException;

public class BlogValidationException extends ValidationException {
    public BlogValidationException(String message) {
        super(message);
    }

}
