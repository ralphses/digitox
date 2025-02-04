package com.clicks.digitox.infrastructure.security.user;

import com.clicks.digitox.domain.user.exceptions.UnauthorizedUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserSecurityServiceImplTest {

    @InjectMocks
    private UserSecurityServiceImpl userSecurityService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccessTokenService accessTokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEncodePassword() {
        String password = "password123";
        String encodedPassword = "encodedPassword123";

        // Mocking the encode method of PasswordEncoder
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        String result = userSecurityService.encodePassword(password);

        assertEquals(encodedPassword, result);
        verify(passwordEncoder, times(1)).encode(password);
    }

    @Test
    void testGetNewAccessToken() {
        String email = "test@example.com";
        String role = "ROLE_USER";
        String token = "newAccessToken123";

        // Mocking the AccessTokenService to return a token
        when(accessTokenService.getNewAccessToken(email, role)).thenReturn(new AccessToken(token, 2000, email));

        String result = userSecurityService.getNewAccessToken(email, role);

        assertEquals(token, result);
        verify(accessTokenService, times(1)).getNewAccessToken(email, role);
    }

    @ParameterizedTest
    @ValueSource(classes = {UsernameNotFoundException.class, BadCredentialsException.class})
    void testAttemptLoginInvalidCredentials(Class<? extends AuthenticationException> exceptionClass) throws Exception {
        String email = "test@example.com";
        String password = "wrongPassword";

        // Mocking authenticationManager to throw different authentication exceptions
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(exceptionClass.getConstructor(String.class).newInstance("error"));

        UnauthorizedUserException exception = assertThrows(UnauthorizedUserException.class, () -> userSecurityService.attemptLogin(email, password));

        assertEquals("Bad login credentials supplied. If you have forgotten your password, try resetting it", exception.getMessage());
    }

    @Test
    void testAttemptLoginDisabledAccount() {
        String email = "test@example.com";
        String password = "password123";

        // Mocking disabled account exception
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new DisabledException("Disabled account"));

        UnauthorizedUserException exception = assertThrows(UnauthorizedUserException.class, () -> {
            userSecurityService.attemptLogin(email, password);
        });

        assertEquals("Authentication failed. Please validate account", exception.getMessage());
    }

    @Test
    void testAttemptLoginLockedAccount() {
        String email = "test@example.com";
        String password = "password123";

        // Mocking locked account exception
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new LockedException("Locked account"));

        UnauthorizedUserException exception = assertThrows(UnauthorizedUserException.class, () -> {
            userSecurityService.attemptLogin(email, password);
        });

        assertEquals("Authentication failed. Account locked! Please contact admin", exception.getMessage());
    }

    @Test
    void testAttemptLoginInternalServerError() {
        String email = "test@example.com";
        String password = "password123";

        // Mocking unexpected exception
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new InternalAuthenticationServiceException("Internal error"));

        UnauthorizedUserException exception = assertThrows(UnauthorizedUserException.class, () -> userSecurityService.attemptLogin(email, password));

        assertEquals("Please complete your registration. Provide first name, last name and email address", exception.getMessage());
    }
}
