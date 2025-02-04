package com.clicks.digitox.infrastructure.security.user;

import com.clicks.digitox.application.user.ports.spi.UserSecurityService;
import com.clicks.digitox.domain.user.exceptions.UnauthorizedUserException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityServiceImpl implements UserSecurityService {

    private final PasswordEncoder passwordEncoder;
    private final AccessTokenService accessTokenService;
    private final AuthenticationManager authenticationManager;

    public UserSecurityServiceImpl(PasswordEncoder passwordEncoder,
                                   AccessTokenService accessTokenService,
                                   AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.accessTokenService = accessTokenService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public String getNewAccessToken(String email, String role) {
        AccessToken accessToken = accessTokenService.getNewAccessToken(email, role);
        return accessToken.token();
    }

    @Override
    public String attemptLogin(String email, String password) {
        try {
            // Authenticate the user using phone number and password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            // Retrieve the user's role/authority
            String role = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .reduce("", (a, b) -> a + b);

            return getNewAccessToken(email, role);

        } catch (InternalAuthenticationServiceException e) {
            throw new UnauthorizedUserException("Please complete your registration. Provide first name, last name and email address");

        } catch (UsernameNotFoundException | BadCredentialsException exception) {
            // Handle bad credentials or username not found
            throw new UnauthorizedUserException("Bad login credentials supplied. If you have forgotten your password, try resetting it");
        } catch (DisabledException e) {
            // Handle disabled account
            throw new UnauthorizedUserException("Authentication failed. Please validate account");
        } catch (LockedException e) {
            // Handle locked account
            throw new UnauthorizedUserException("Authentication failed. Account locked! Please contact admin");
        } catch (Exception e) {
            // Handle any other exceptions
            throw new IllegalStateException("Authentication failed, please contact admin");
        }
    }

}
