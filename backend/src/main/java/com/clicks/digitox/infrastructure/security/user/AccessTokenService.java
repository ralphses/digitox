package com.clicks.digitox.infrastructure.security.user;

import com.clicks.digitox.shared.persistence.GenericInMemoryStorageService;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.clicks.digitox.shared.enums.StorageKeys.ACCESS_TOKEN_;
import static java.time.Instant.now;
import static org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS512;

@Service
public class AccessTokenService {

    public static final String ISSUER = "digitox";

    private final JwtEncoder jwtEncoder;
    private final GenericInMemoryStorageService genericInMemoryStorageService;

    public AccessTokenService(GenericInMemoryStorageService genericInMemoryStorageService, JwtEncoder jwtEncoder) {
        this.genericInMemoryStorageService = genericInMemoryStorageService;
        this.jwtEncoder = jwtEncoder;
    }


    public AccessToken getNewAccessToken(String email, String role) {

        return Optional.ofNullable((AccessToken) genericInMemoryStorageService.retrieveData(email))
                .orElseGet(() -> createNewAccessToken(email, role));
    }

    private AccessToken createNewAccessToken(String email, String role) {

        Instant now = now();
        Instant expiresAt = now.plus(1, ChronoUnit.DAYS);

        // Create new token
        String tokenValue = generateNewAccessToken(email, role, now, expiresAt);
        AccessToken token = new AccessToken(tokenValue, expiresAt.toEpochMilli(), email);

        // Persist token
        saveToken(token);

        // return saved token
        return token;
    }

    private void saveToken(AccessToken token) {
        genericInMemoryStorageService.storeTemporaryData(
                ACCESS_TOKEN_.name() + token.user(),
                token,
                23,
                TimeUnit.HOURS
        );
    }

    private String generateNewAccessToken(String email, String role, Instant createdAt, Instant expiresAt) {

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer(ISSUER)
                .issuedAt(createdAt)
                .expiresAt(expiresAt)
                .subject(email)
                .claim("scope", role)
                .build();

        JwtEncoderParameters encoderParams = JwtEncoderParameters.from(JwsHeader.with(HS512).build(), claimsSet);
        return jwtEncoder.encode(encoderParams).getTokenValue();
    }
}
