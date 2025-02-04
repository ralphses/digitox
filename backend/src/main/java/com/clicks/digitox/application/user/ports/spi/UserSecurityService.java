package com.clicks.digitox.application.user.ports.spi;


public interface UserSecurityService {

    String encodePassword(String password);
    String getNewAccessToken(String email, String role);
    String attemptLogin(String email, String password);
}
