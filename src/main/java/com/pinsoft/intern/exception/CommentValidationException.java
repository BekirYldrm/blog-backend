package com.pinsoft.intern.exception;

import jakarta.validation.ValidationException;

public class CommentValidationException extends ValidationException {
    public CommentValidationException(String message) {
        super(message);
    }
}
