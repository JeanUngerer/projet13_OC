package com.yourcaryourway.chatappback.security.controller;



import com.yourcaryourway.chatappback.dtos.requests.RefreshTokenRequest;
import com.yourcaryourway.chatappback.dtos.responses.AuthInfoResponse;
import com.yourcaryourway.chatappback.dtos.responses.AuthRefreshResponse;
import com.yourcaryourway.chatappback.entities.RefreshToken;
import com.yourcaryourway.chatappback.entities.User;
import com.yourcaryourway.chatappback.exceptions.ForbidenExceptionHandler;
import com.yourcaryourway.chatappback.security.services.RefreshTokenService;
import com.yourcaryourway.chatappback.security.services.TokenService;
import com.yourcaryourway.chatappback.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * API controller for handling authentication requests.
 */
@RestController
public class AuthController {

    private final TokenService tokenService;

    private final RefreshTokenService refreshTokenService;

    private final UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Constructor for AuthController.
     *
     * @param tokenService the JWT token service
     * @param refreshTokenService the refresh token service
     * @param userService the user service
     */
    public AuthController(TokenService tokenService, RefreshTokenService refreshTokenService, UserService userService) {
        this.tokenService = tokenService;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
    }


    /**
     * Endpoint for generating a JWT token.
     *
     * @param authentication the Authentication object containing the user's credentials
     * @return ResponseEntity with authorization information
     */
    @PostMapping("/token")
    public ResponseEntity<AuthInfoResponse> token(Authentication authentication) {
        String token = tokenService.generateTokenFromAuthentication(authentication);
        String refreshToken = refreshTokenService.createRefreshToken(authentication.getName()).getToken();
        User user = userService.findUserByEmail(authentication.getName());
        return ResponseEntity.ok(new AuthInfoResponse(token, refreshToken, user.getIsCustomer()));
    }

    /**
     * Endpoint for refreshing a JWT token.
     *
     * @param refreshTokenRequest the RefreshTokenRequest containing the old refresh token
     * @return ResponseEntity with the new authorization information
     */
    @PostMapping("/refreshtoken")
    public ResponseEntity<AuthRefreshResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .orElseThrow(() ->new ForbidenExceptionHandler("No Refresh Token"));

        User gimUser = refreshToken.getUserInfo();

        refreshTokenService.verifyExpiration(refreshToken);
        String token = tokenService.generateTokenFromUsername(gimUser.getEmail());
        return ResponseEntity.ok(new AuthRefreshResponse(token));
    }
}