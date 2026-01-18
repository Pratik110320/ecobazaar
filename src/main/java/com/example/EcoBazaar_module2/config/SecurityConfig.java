package com.example.EcoBazaar_module2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        // ---------- PUBLIC ENDPOINTS ----------
                        .requestMatchers(
                                "/",
                                "/index.html",
                                "/static/**",
                                "/*.ico",
                                "/*.png",
                                "/*.json",
                                "/api/auth/**",
                                "/api/images/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/error",
                                "/favicon.ico"
                        ).permitAll()

                        // ---------- PRODUCTS - PUBLIC (Read only) ----------
                        .requestMatchers(
                                "/api/products",
                                "/api/products/**",
                                "/api/categories",
                                "/api/categories/**"
                        ).permitAll()

                        // ---------- COMMUNITY - PUBLIC ----------
                        .requestMatchers(
                                "/api/community/**",
                                "/api/carbon/report/**"
                        ).permitAll()

                        // ---------- USER ENDPOINTS - REQUIRE AUTH ----------
                        .requestMatchers(
                                "/api/cart/**",
                                "/api/wishlist/**",
                                "/api/orders/**",
                                "/api/dashboard/user/**",
                                "/api/reviews/**"
                        ).authenticated()

                        // ---------- SELLER ENDPOINTS ----------
                        .requestMatchers(
                                "/api/dashboard/seller/**",
                                "/api/seller/**"
                        ).hasAnyRole("SELLER", "ADMIN")

                        // ---------- ADMIN ENDPOINTS ----------
                        .requestMatchers(
                                "/api/admin/**",
                                "/api/dashboard/admin/**"
                        ).hasRole("ADMIN")

                        // ---------- EVERYTHING ELSE ----------
                        .anyRequest().authenticated()
                )

                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        // Read allowed origins from environment variable with fallback
        String allowedOriginsEnv = System.getenv("ALLOWED_ORIGINS");

        List<String> allowedOrigins;

        if (allowedOriginsEnv != null && !allowedOriginsEnv.isBlank()) {
            // Split by comma and trim whitespace
            allowedOrigins = Arrays.stream(allowedOriginsEnv.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());

            System.out.println("✓ CORS: Using environment variable ALLOWED_ORIGINS");
            System.out.println("✓ Allowed origins: " + allowedOrigins);
        } else {
            // Fallback to hardcoded values for local development
            allowedOrigins = Arrays.asList(
                    "http://localhost:3000",
                    "http://localhost:5173",
                    "http://localhost:4200",
                    "http://localhost:8080"
            );

            System.out.println("⚠ CORS: No ALLOWED_ORIGINS env var found, using default localhost origins");
            System.out.println("✓ Allowed origins: " + allowedOrigins);
        }

        configuration.setAllowedOrigins(allowedOrigins);

        // Allow all common HTTP methods
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"
        ));

        // Allow all common headers
        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"
        ));

        // Expose headers to the client
        configuration.setExposedHeaders(Arrays.asList(
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials",
                "Authorization"
        ));

        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}