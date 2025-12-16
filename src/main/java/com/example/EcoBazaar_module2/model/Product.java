package com.example.EcoBazaar_module2.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;
    private String imageUrl;

    // Module 2: Carbon Data
    private Double carbonFootprint; // in kg CO2e
    private String ecoRating; // "A+", "A", "B", "C"

    // Category is crucial for finding "Greener Alternatives"
    private String category;

    // Module 5: Admin Verification
    private boolean isVerified;
    private Long sellerId;

    // Helper to determine if this is a "Green" product
    public boolean isEcoFriendly() {
        return "A+".equals(ecoRating) || "A".equals(ecoRating);
    }
}