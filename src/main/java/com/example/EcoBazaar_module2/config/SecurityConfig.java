package com.example.EcoBazaar_module2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {}) // IMPORTANT: enable CORS in security

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/index.html",
                                "/api/auth/**",
                                "/css/**",
                                "/js/**",
                                "/api/images/**"
                        ).permitAll()
                        .anyRequest().permitAll()
                )

                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}
