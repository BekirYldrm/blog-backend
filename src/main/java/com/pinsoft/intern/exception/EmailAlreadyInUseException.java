package com.pinsoft.intern.exception;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException() {
        super("The provided email is already in use by another user.");
    }
}
