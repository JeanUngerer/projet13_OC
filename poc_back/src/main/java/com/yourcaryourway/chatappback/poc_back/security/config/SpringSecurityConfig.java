package com.yourcaryourway.chatappback.poc_back.security.config;


import com.yourcaryourway.chatappback.poc_back.properties.RsaKeyProperties;
import com.yourcaryourway.chatappback.poc_back.security.jwt.JwtHandling;
import com.yourcaryourway.chatappback.poc_back.services.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@Getter
@Setter
public class SpringSecurityConfig {

    private final PasswordEncoder encoder;

    @Autowired
    JwtHandling jwtHandling;

    private final UserService myUserDetailsService;

    public SpringSecurityConfig(PasswordEncoder encoder, RsaKeyProperties rsaKeys, UserService myUserDetailsService) {
        this.encoder = encoder;
        this.myUserDetailsService = myUserDetailsService;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(httpSecurityCorsConfigurer -> corsConfigurationSource())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/images/**",
                                "/javainuse-openapi/**", "/register", "/token", "/refreshtoken").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(myUserDetailsService)
                .httpBasic(withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtHandling.jwtDecoder())))
                .build();
    }



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        configuration.addExposedHeader("Token");
        configuration.addAllowedMethod(HttpMethod.PUT);
        configuration.addAllowedMethod(HttpMethod.DELETE);
        configuration.addAllowedMethod(HttpMethod.POST);
        configuration.addAllowedMethod(HttpMethod.GET);

        configuration.setAllowCredentials(true);

        configuration.setAllowedOrigins( Collections.singletonList( "http://localhost:4200" ) );
        configuration.addAllowedOrigin ( "http://localhost:9000" );
        configuration.addAllowedOriginPattern ( "*" );

        source.registerCorsConfiguration("/**",configuration);
        return source;
    }

}
