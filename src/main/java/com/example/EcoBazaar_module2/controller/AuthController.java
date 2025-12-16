package com.example.EcoBazaar_module2.controller;


import com.example.EcoBazaar_module2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Simple Repository definition inside file for compactness
interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Module 1: Registration
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Error: Email already in use!");
        }

        // Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Default stats
        user.setTotalCarbonSaved(0.0);
        user.setEcoScore(0);

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    // Module 1: Login (Simplified for prototype - Returns user object/role)
    // In production, this would return a JWT Token.
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());

        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.ok(user); // Send back user info + Role
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    // DTO
    static class LoginRequest {
        private String email;
        private String password;
        // getters setters...
        public String getEmail() { return email; }
        public String getPassword() { return password; }
    }
}