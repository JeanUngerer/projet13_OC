package com.yourcaryourway.chatappback.poc_back.security.controller;


import com.ts.gim.dtos.requests.RefreshTokenRequest;
import com.ts.gim.dtos.responses.AuthInfoResponse;
import com.ts.gim.dtos.responses.AuthRefreshResponse;
import com.ts.gim.entities.GimUser;
import com.ts.gim.entities.RefreshToken;
import com.ts.gim.exceptions.ForbidenExceptionHandler;
import com.ts.gim.security.services.RefreshTokenService;
import com.ts.gim.security.services.TokenService;
import com.ts.gim.services.GimUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@SecurityScheme(
        name = "basicAuth", // can be set to anything
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class AuthController {

    private final TokenService tokenService;

    private final RefreshTokenService refreshTokenService;

    private final GimUserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Constructor for AuthController.
     *
     * @param tokenService the JWT token service
     * @param refreshTokenService the refresh token service
     * @param userService the user service
     */
    public AuthController(TokenService tokenService, RefreshTokenService refreshTokenService, GimUserService userService) {
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
    @Operation(summary = "Get JWT token", description = "Get JWT token")
    @SecurityRequirement(name = "basicAuth")
    @PostMapping("/token")
    public ResponseEntity<AuthInfoResponse> token(Authentication authentication) {
        log.info("Token requested for user : " + authentication.getName());
        String token = tokenService.generateTokenFromAuthentication(authentication);
        String refreshToken = refreshTokenService.createRefreshToken(authentication.getName()).getToken();
        log.info("Token granted ");

        return ResponseEntity.ok(new AuthInfoResponse(token, refreshToken));
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

        GimUser gimUser = refreshToken.getGimUserInfo();
        log.info("Token refreshment requested for user : " + gimUser.getEmail());

        refreshTokenService.verifyExpiration(refreshToken);
        String token = tokenService.generateTokenFromUsername(gimUser.getEmail());
        log.info("Token refreshment granted ");
        return ResponseEntity.ok(new AuthRefreshResponse(token));
    }
}