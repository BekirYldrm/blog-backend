package com.pinsoft.intern.exception;

import jakarta.validation.ValidationException;

public class PasswordMatchException extends ValidationException {
    public PasswordMatchException() {
        super("New password cannot be the same as the old password");
    }
}
