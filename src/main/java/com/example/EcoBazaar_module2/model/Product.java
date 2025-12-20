package com.example.EcoBazaar_module2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity = 1;

    private String imageUrl; // Kept for URL compatibility

    private String imagePath; // New field for local file path

    private String category;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private boolean verified = false;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductCarbonData carbonData;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Computed field
    @Transient
    public Double getTotalCarbonFootprint() {
        return carbonData != null ? carbonData.getTotalCO2e() : 0.0;
    }

    @Transient
    public String getEcoRating() {
        Double total = getTotalCarbonFootprint();
        if (total < 2.0) return "A+";
        else if (total < 5.0) return "B";
        else return "C";
    }
}