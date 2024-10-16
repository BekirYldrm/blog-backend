package com.pinsoft.intern.exception;

import jakarta.validation.ValidationException;

public class EmailMatchException extends ValidationException {
    public EmailMatchException() {
        super("The new email address cannot be the same as the old email address.");
    }
}
