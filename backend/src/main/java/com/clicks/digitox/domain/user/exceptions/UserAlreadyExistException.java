package com.clicks.digitox.domain.user.exceptions;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String email) {
        super("User with email address " + email + " already exists");
    }
}
