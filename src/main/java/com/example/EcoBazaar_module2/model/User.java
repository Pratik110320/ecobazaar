package com.example.EcoBazaar_module2.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String fullName;

    // Role: "CONSUMER", "SELLER", "ADMIN"
    private String role;

    // Module 4: Dashboard Stats
    private Double totalCarbonSaved = 0.0;
    private Integer ecoScore = 0; // Gamification points
    private Integer greenPurchasesCount = 0;

    public void addCarbonSaved(double amount) {
        this.totalCarbonSaved += amount;
        this.ecoScore += (int) (amount * 10); // 10 points per kg saved
        this.greenPurchasesCount++;
    }
}