package com.example.EcoBazaar_module2.controller;

import com.example.EcoBazaar_module2.model.Role;
import com.example.EcoBazaar_module2.model.User;
import com.example.EcoBazaar_module2.service.AuthService;
import com.example.EcoBazaar_module2.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Register new user
     * IMPORTANT: Only SELLER and USER roles can be created
     * ADMIN role cannot be created through registration
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");
            String fullName = request.get("fullName");
            String roleStr = request.get("role");

            // Validate required fields
            if (email == null || password == null || fullName == null || roleStr == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "All fields are required: email, password, fullName, role"
                ));
            }

            // Validate role
            Role role;
            try {
                role = Role.valueOf(roleStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "Invalid role. Only SELLER and USER (Shopper) roles are allowed."
                ));
            }

            // The AuthService will validate that only SELLER and USER roles are allowed
            User user = authService.registerUser(email, password, fullName, role);

            return ResponseEntity.ok(Map.of(
                    "message", "User registered successfully",
                    "userId", user.getId(),
                    "role", user.getRole().toString()
            ));
        } catch (RuntimeException e) {
            // Handle role restriction and other errors
            return ResponseEntity.badRequest().body(Map.of(
                    "error", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Registration failed: " + e.getMessage()
            ));
        }
    }

    /**
     * Login user
     * Works for all roles including ADMIN
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");

            // Validate required fields
            if (email == null || password == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "Email and password are required"
                ));
            }

            // Authenticate user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // Load user details
            final UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Generate JWT token
            final String jwt = jwtUtil.generateToken(userDetails);

            User user = authService.authenticateUser(email, password);

            // Return user info and token
            return ResponseEntity.ok(Map.of(
                    "token", jwt,
                    "user", Map.of(
                            "id", user.getId(),
                            "email", user.getEmail(),
                            "fullName", user.getFullName(),
                            "role", user.getRole().toString()
                    )
            ));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(Map.of(
                    "error", "Invalid credentials"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * Get allowed roles for registration
     * Returns list of roles that can be created via signup
     */
    @GetMapping("/allowed-roles")
    public ResponseEntity<?> getAllowedRoles() {
        return ResponseEntity.ok(Map.of(
                "allowedRoles", new String[]{"SELLER", "USER"},
                "message", "Only SELLER and USER roles can be created through registration"
        ));
    }

    /**
     * Forgot Password - Request password reset
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");

            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "Email is required"
                ));
            }

            authService.initiatePasswordReset(email);
            return ResponseEntity.ok(Map.of(
                    "message", "Password reset link sent to your email if the account exists"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * Reset Password - Reset password with token
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        try {
            String token = request.get("token");
            String newPassword = request.get("newPassword");

            if (token == null || newPassword == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "Token and new password are required"
                ));
            }

            if (newPassword.length() < 6) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "Password must be at least 6 characters long"
                ));
            }

            authService.resetPassword(token, newPassword);

            return ResponseEntity.ok(Map.of(
                    "message", "Password reset successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * Validate Reset Token
     */
    @GetMapping("/validate-token/{token}")
    public ResponseEntity<?> validateToken(@PathVariable String token) {
        try {
            boolean isValid = authService.validatePasswordResetToken(token);
            return ResponseEntity.ok(Map.of(
                    "valid", isValid
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }
}
