// File: JwtRequestFilter.java
package com.example.EcoBazaar_module2.config;

import com.example.EcoBazaar_module2.service.CustomUserDetailsService;
import com.example.EcoBazaar_module2.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    // List of public endpoints that don't require authentication
    private static final List<String> PUBLIC_ENDPOINTS = Arrays.asList(
            "/api/auth/",
            "/api/products",
            "/api/categories",
            "/api/community/",
            "/api/carbon/report/",
            "/error",
            "/favicon.ico",
            "/v3/api-docs",
            "/swagger-ui"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Get the request URI
        String requestURI = request.getRequestURI();

        // Log request for debugging (remove in production)
        System.out.println("📨 Request: " + request.getMethod() + " " + requestURI);

        // Skip JWT validation for public endpoints
        if (isPublicEndpoint(requestURI)) {
            System.out.println("✓ Public endpoint - skipping JWT validation");
            chain.doFilter(request, response);
            return;
        }

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwtToken);
                System.out.println("✓ JWT Token found for user: " + username);
            } catch (Exception e) {
                System.err.println("❌ JWT Token error: " + e.getMessage());
                logger.error("Unable to get JWT Token", e);
            }
        } else {
            System.out.println("⚠ No JWT Token found in Authorization header for: " + requestURI);
        }

        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // if token is valid configure Spring Security to manually set authentication
            if (jwtUtil.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                System.out.println("✓ User authenticated: " + username + " with roles: " + userDetails.getAuthorities());
            } else {
                System.out.println("❌ Invalid JWT token for user: " + username);
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * Check if the request URI is a public endpoint
     */
    private boolean isPublicEndpoint(String requestURI) {
        return PUBLIC_ENDPOINTS.stream().anyMatch(requestURI::startsWith);
    }
}