package com.clicks.digitox.infrastructure.security.user;

public record AccessToken(String token, long expiresAt, boolean revoked, String user) {

    public AccessToken(String token, long expiresAt, String user) {
        this(token, expiresAt, false, user); // Set revoked to false by default
    }
}

