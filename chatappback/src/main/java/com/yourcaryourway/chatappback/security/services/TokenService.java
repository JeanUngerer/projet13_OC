package com.yourcaryourway.chatappback.security.services;

import com.yourcaryourway.chatappback.exceptions.BadRequestExceptionHandler;
import com.yourcaryourway.chatappback.exceptions.ForbidenExceptionHandler;
import com.yourcaryourway.chatappback.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * A service for creating, encoding, and decoding a JWT token
 */
@Service
public class TokenService {

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;
    private final UserService userService;

    /**
     * The constructor for TokenService class
     *
     * @param encoder The JWT encoder
     * @param decoder The JWT decoder
     * @param userService The service for managing user data
     */
    public TokenService(JwtEncoder encoder, JwtDecoder decoder, UserService userService) {
        this.encoder = encoder;
        this.decoder = decoder;
        this.userService = userService;
    }

    /**
     * Generates a JWT token string from an authentication object
     *
     * @param authentication the authentication object
     * @return a JWT token string
     */
    public String generateTokenFromAuthentication(Authentication authentication) {
        try {
            return generateToken(authentication.getName(), authentication.getAuthorities());
        } catch (Exception e) {
            throw new ForbidenExceptionHandler("Error generating token from authentication", e);
        }
    }

    /**
     * Generates a JWT token string from a username
     *
     * @param username The username
     * @return a JWT token string
     */
    public String generateTokenFromUsername(String username) {
        try {
            UserDetails userDetails = userService.loadUserByUsername(username);
            return generateToken(userDetails.getUsername(), userDetails.getAuthorities());
        } catch (Exception e) {
            throw new ForbidenExceptionHandler("Error generating token from username", e);
        }
    }

    /**
     * Decodes the username from a JWT token
     *
     * @param token The JWT token
     * @return the username found inside the JWT token
     */
    public String decodeTokenUsername(String token) {
        try {
            return this.decoder.decode(token.substring(7)).getSubject();
        } catch (Exception e) {
            throw new BadRequestExceptionHandler("Error decoding token username", e);
        }
    }

    /**
     * Generates a JWT token string from a username and a collection of user's authorities
     *
     * @param username The username
     * @param authorities The user's authorities
     * @return a JWT token string
     */
    private String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        try {
            Instant now = Instant.now();
            long expiry = 604800;
            String scope = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
            JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(username)
                .claim("scope", scope)
                .build();
            return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        } catch (Exception e) {
            throw new ForbidenExceptionHandler("Error generating token", e);
        }
    }
}