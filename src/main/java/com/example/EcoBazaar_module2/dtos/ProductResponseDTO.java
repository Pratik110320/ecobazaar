package com.example.EcoBazaar_module2.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private String imageBase64; // Changed from imageUrl
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

    public ProductResponseDTO() {}

    // Updated constructor
    public ProductResponseDTO(Long id, String name, String description, BigDecimal price, Long sellerId, String sellerName, String category, String subCategory, String brand, String imageBase64, Integer stockQuantity, BigDecimal weightKg, String dimensions, String manufacturingLocation, BigDecimal carbonImpact, String carbonCalculationMethod, String carbonBreakdown, Boolean ecoCertified, String ecoCertificationDetails, BigDecimal ecoRating, String ecoLabel, String ecoBadgeColor, Boolean recyclable, Boolean biodegradable, Boolean renewableEnergyUsed, Boolean shippingCarbonOffset, Boolean active, Boolean verified, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.category = category;
        this.subCategory = subCategory;
        this.brand = brand;
        this.imageBase64 = imageBase64; // Changed
        this.stockQuantity = stockQuantity;
        this.weightKg = weightKg;
        this.dimensions = dimensions;
        this.manufacturingLocation = manufacturingLocation;
        this.carbonImpact = carbonImpact;
        this.carbonCalculationMethod = carbonCalculationMethod;
        this.carbonBreakdown = carbonBreakdown;
        this.ecoCertified = ecoCertified;
        this.ecoCertificationDetails = ecoCertificationDetails;
        this.ecoRating = ecoRating;
        this.ecoLabel = ecoLabel;
        this.ecoBadgeColor = ecoBadgeColor;
        this.recyclable = recyclable;
        this.biodegradable = biodegradable;
        this.renewableEnergyUsed = renewableEnergyUsed;
        this.shippingCarbonOffset = shippingCarbonOffset;
        this.active = active;
        this.verified = verified;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Long getSellerId() { return sellerId; }
    public void setSellerId(Long sellerId) { this.sellerId = sellerId; }

    public String getSellerName() { return sellerName; }
    public void setSellerName(String sellerName) { this.sellerName = sellerName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSubCategory() { return subCategory; }
    public void setSubCategory(String subCategory) { this.subCategory = subCategory; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getImageBase64() { return imageBase64; } // Changed
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; } // Changed

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }

    public BigDecimal getWeightKg() { return weightKg; }
    public void setWeightKg(BigDecimal weightKg) { this.weightKg = weightKg; }

    public String getDimensions() { return dimensions; }
    public void setDimensions(String dimensions) { this.dimensions = dimensions; }

    public String getManufacturingLocation() { return manufacturingLocation; }
    public void setManufacturingLocation(String manufacturingLocation) { this.manufacturingLocation = manufacturingLocation; }

    public BigDecimal getCarbonImpact() { return carbonImpact; }
    public void setCarbonImpact(BigDecimal carbonImpact) { this.carbonImpact = carbonImpact; }

    public String getCarbonCalculationMethod() { return carbonCalculationMethod; }
    public void setCarbonCalculationMethod(String carbonCalculationMethod) { this.carbonCalculationMethod = carbonCalculationMethod; }

    public String getCarbonBreakdown() { return carbonBreakdown; }
    public void setCarbonBreakdown(String carbonBreakdown) { this.carbonBreakdown = carbonBreakdown; }

    public Boolean getEcoCertified() { return ecoCertified; }
    public void setEcoCertified(Boolean ecoCertified) { this.ecoCertified = ecoCertified; }

    public String getEcoCertificationDetails() { return ecoCertificationDetails; }
    public void setEcoCertificationDetails(String ecoCertificationDetails) { this.ecoCertificationDetails = ecoCertificationDetails; }

    public BigDecimal getEcoRating() { return ecoRating; }
    public void setEcoRating(BigDecimal ecoRating) { this.ecoRating = ecoRating; }

    public String getEcoLabel() { return ecoLabel; }
    public void setEcoLabel(String ecoLabel) { this.ecoLabel = ecoLabel; }

    public String getEcoBadgeColor() { return ecoBadgeColor; }
    public void setEcoBadgeColor(String ecoBadgeColor) { this.ecoBadgeColor = ecoBadgeColor; }

    public Boolean getRecyclable() { return recyclable; }
    public void setRecyclable(Boolean recyclable) { this.recyclable = recyclable; }

    public Boolean getBiodegradable() { return biodegradable; }
    public void setBiodegradable(Boolean biodegradable) { this.biodegradable = biodegradable; }

    public Boolean getRenewableEnergyUsed() { return renewableEnergyUsed; }
    public void setRenewableEnergyUsed(Boolean renewableEnergyUsed) { this.renewableEnergyUsed = renewableEnergyUsed; }

    public Boolean getShippingCarbonOffset() { return shippingCarbonOffset; }
    public void setShippingCarbonOffset(Boolean shippingCarbonOffset) { this.shippingCarbonOffset = shippingCarbonOffset; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public Boolean getVerified() { return verified; }
    public void setVerified(Boolean verified) { this.verified = verified; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Manual builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Long sellerId;
        private String sellerName;
        private String category;
        private String subCategory;
        private String brand;
        private String imageBase64; // Changed
        private Integer stockQuantity;
        private BigDecimal weightKg;
        private String dimensions;
        private String manufacturingLocation;
        private BigDecimal carbonImpact;
        private String carbonCalculationMethod;
        private String carbonBreakdown;
        private Boolean ecoCertified;
        private String ecoCertificationDetails;
        private BigDecimal ecoRating;
        private String ecoLabel;
        private String ecoBadgeColor;
        private Boolean recyclable;
        private Boolean biodegradable;
        private Boolean renewableEnergyUsed;
        private Boolean shippingCarbonOffset;
        private Boolean active;
        private Boolean verified;
        private java.time.LocalDateTime createdAt;
        private java.time.LocalDateTime updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder price(BigDecimal price) { this.price = price; return this; }
        public Builder sellerId(Long sellerId) { this.sellerId = sellerId; return this; }
        public Builder sellerName(String sellerName) { this.sellerName = sellerName; return this; }
        public Builder category(String category) { this.category = category; return this; }
        public Builder subCategory(String subCategory) { this.subCategory = subCategory; return this; }
        public Builder brand(String brand) { this.brand = brand; return this; }
        public Builder imageBase64(String imageBase64) { this.imageBase64 = imageBase64; return this; } // Changed
        public Builder stockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; return this; }
        public Builder weightKg(BigDecimal weightKg) { this.weightKg = weightKg; return this; }
        public Builder dimensions(String dimensions) { this.dimensions = dimensions; return this; }
        public Builder manufacturingLocation(String manufacturingLocation) { this.manufacturingLocation = manufacturingLocation; return this; }
        public Builder carbonImpact(BigDecimal carbonImpact) { this.carbonImpact = carbonImpact; return this; }
        public Builder carbonCalculationMethod(String carbonCalculationMethod) { this.carbonCalculationMethod = carbonCalculationMethod; return this; }
        public Builder carbonBreakdown(String carbonBreakdown) { this.carbonBreakdown = carbonBreakdown; return this; }
        public Builder ecoCertified(Boolean ecoCertified) { this.ecoCertified = ecoCertified; return this; }
        public Builder ecoCertificationDetails(String ecoCertificationDetails) { this.ecoCertificationDetails = ecoCertificationDetails; return this; }
        public Builder ecoRating(BigDecimal ecoRating) { this.ecoRating = ecoRating; return this; }
        public Builder ecoLabel(String ecoLabel) { this.ecoLabel = ecoLabel; return this; }
        public Builder ecoBadgeColor(String ecoBadgeColor) { this.ecoBadgeColor = ecoBadgeColor; return this; }
        public Builder recyclable(Boolean recyclable) { this.recyclable = recyclable; return this; }
        public Builder biodegradable(Boolean biodegradable) { this.biodegradable = biodegradable; return this; }
        public Builder renewableEnergyUsed(Boolean renewableEnergyUsed) { this.renewableEnergyUsed = renewableEnergyUsed; return this; }
        public Builder shippingCarbonOffset(Boolean shippingCarbonOffset) { this.shippingCarbonOffset = shippingCarbonOffset; return this; }
        public Builder active(Boolean active) { this.active = active; return this; }
        public Builder verified(Boolean verified) { this.verified = verified; return this; }
        public Builder createdAt(java.time.LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(java.time.LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public ProductResponseDTO build() {
            return new ProductResponseDTO(id, name, description, price, sellerId, sellerName, category, subCategory, brand, imageBase64, stockQuantity, weightKg, dimensions, manufacturingLocation, carbonImpact, carbonCalculationMethod, carbonBreakdown, ecoCertified, ecoCertificationDetails, ecoRating, ecoLabel, ecoBadgeColor, recyclable, biodegradable, renewableEnergyUsed, shippingCarbonOffset, active, verified, createdAt, updatedAt);
        }
    }
}