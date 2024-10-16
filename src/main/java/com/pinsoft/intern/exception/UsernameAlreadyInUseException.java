package com.pinsoft.intern.exception;

public class UsernameAlreadyInUseException extends RuntimeException {
    public UsernameAlreadyInUseException() {
        super("The provided username is already in use by another user.");
    }
}
