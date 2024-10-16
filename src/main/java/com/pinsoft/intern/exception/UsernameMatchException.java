package com.pinsoft.intern.exception;

import jakarta.validation.ValidationException;

public class UsernameMatchException extends ValidationException {
    public UsernameMatchException() {
        super("New username cannot be the same as the old username");
    }
}
