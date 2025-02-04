package com.clicks.digitox.domain.user.exceptions;

public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException() {
        super("Unauthorized user operation");
    }

    public UnauthorizedUserException(String message) {
        super(message);
    }
}
