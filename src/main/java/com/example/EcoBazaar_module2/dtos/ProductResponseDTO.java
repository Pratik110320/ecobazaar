package com.example.EcoBazaar_module2.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long sellerId;
    private String sellerName;

    private String category;
    private String subCategory;
    private String brand;
    private String imageUrl; // Changed from imageBase64 to imageUrl
    private Integer stockQuantity;
    private BigDecimal weightKg;
    private String dimensions;
    private String manufacturingLocation;

    // Carbon data
    private BigDecimal carbonImpact;
    private String carbonCalculationMethod;
    private String carbonBreakdown;

    // Eco information
    private Boolean ecoCertified;
    private String ecoCertificationDetails;
    private BigDecimal ecoRating;
    private String ecoLabel;
    private String ecoBadgeColor;

    // Eco features
    private Boolean recyclable;
    private Boolean biodegradable;
    private Boolean renewableEnergyUsed;
    private Boolean shippingCarbonOffset;

    // Status
    private Boolean active;
    private Boolean verified;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}