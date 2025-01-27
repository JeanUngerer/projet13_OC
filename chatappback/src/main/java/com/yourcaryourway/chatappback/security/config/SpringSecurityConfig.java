package com.yourcaryourway.chatappback.security.config;


import com.yourcaryourway.chatappback.properties.RsaKeyProperties;
import com.yourcaryourway.chatappback.security.jwt.JwtHandling;
import com.yourcaryourway.chatappback.services.UserService;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;
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
                                "/javainuse-openapi/**", "/register", "/token", "/refreshtoken", "/chat-websocket").permitAll()
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
        configuration.addAllowedMethod(GET);

        configuration.setAllowCredentials(true);

        List<String> allowedOrigins = new ArrayList<>();
        allowedOrigins.add("http://localhost:4200");
        allowedOrigins.add("http://localhost:8087");
        //configuration.setAllowedOrigins( allowedOrigins );

        configuration.addAllowedOriginPattern("http://localhost:*");



        source.registerCorsConfiguration("/**",configuration);
        return source;
    }

}
