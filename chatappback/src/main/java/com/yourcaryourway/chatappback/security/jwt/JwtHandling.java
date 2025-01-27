package com.yourcaryourway.chatappback.security.jwt;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.yourcaryourway.chatappback.properties.RsaKeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

/**
 * Configuration class for handling JWT related operations.
 */
@Configuration
public class JwtHandling {

    private final RsaKeyProperties rsaKeys;

    /**
     * Constructs a new instance of JwtHandling.
     *
     * @param rsaKeys The RSA keys properties.
     */
    public JwtHandling(RsaKeyProperties rsaKeys) {
        this.rsaKeys = rsaKeys;
    }

    /**
     * JWT Decoder bean.
     *
     * @return JwtDecoder instance.
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    /**
     * JWT Decoder bean.
     *
     * @return JwtDecoder instance.
     */
    @Bean
    JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
}