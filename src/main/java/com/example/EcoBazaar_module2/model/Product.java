package com.example.EcoBazaar_module2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 200)
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Product description is required")
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Seller ID is required")
    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @Column(name = "seller_name")
    private String sellerName;

    // Carbon Footprint Data
    @NotNull(message = "Carbon impact is required")
    @DecimalMin(value = "0.0")
    @Column(name = "carbon_impact", nullable = false, precision = 10, scale = 3)
    private BigDecimal carbonImpact; // CO2e in kg

    @Column(name = "carbon_calculation_method")
    @Enumerated(EnumType.STRING)
    private CarbonCalculationMethod carbonCalculationMethod;

    @Column(name = "carbon_breakdown", columnDefinition = "TEXT")
    private String carbonBreakdown; // JSON string with detailed breakdown

    @Column(name = "eco_certified")
    private Boolean ecoCertified = false;

    @Column(name = "eco_certification_details")
    private String ecoCertificationDetails;

    @Column(name = "eco_rating")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    private BigDecimal ecoRating; // 0-5 scale

    // Product Details
    @Column(name = "category")
    private String category;

    @Column(name = "sub_category")
    private String subCategory;

    @Column(name = "brand")
    private String brand;

    // Store image data directly in the database
    // We remove @Lob (which defaults to 'oid') and explicitly define the column as 'bytea'.
    // This is the standard way to map byte[] to bytea in PostgreSQL
    // and should fix the parameter binding order error.
    @Column(name = "image_data", columnDefinition = "bytea")
    private byte[] imageData;

    @Column(name = "stock_quantity")
    private Integer stockQuantity = 0;

    @Column(name = "weight_kg", precision = 10, scale = 3)
    private BigDecimal weightKg;

    @Column(name = "dimensions")
    private String dimensions; // e.g., "10x20x30 cm"

    @Column(name = "manufacturing_location")
    private String manufacturingLocation;

    @Column(name = "shipping_carbon_offset")
    private Boolean shippingCarbonOffset = false;

    @Column(name = "recyclable")
    private Boolean recyclable = false;

    @Column(name = "biodegradable")
    private Boolean biodegradable = false;

    @Column(name = "renewable_energy_used")
    private Boolean renewableEnergyUsed = false;

    // Status
    @Column(name = "active")
    private Boolean active = true;

    @Column(name = "verified")
    private Boolean verified = false;

    @Column(name = "verification_date")
    private LocalDateTime verificationDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Enums
    public enum CarbonCalculationMethod {
        MANUAL_INPUT,
        API_CALCULATED,
        LCA_CERTIFIED,
        ESTIMATED
    }

    public Product() {}

    // Updated constructor
    public Product(Long id, String name, String description, BigDecimal price, Long sellerId, String sellerName, BigDecimal carbonImpact, CarbonCalculationMethod carbonCalculationMethod, String carbonBreakdown, Boolean ecoCertified, String ecoCertificationDetails, BigDecimal ecoRating, String category, String subCategory, String brand, byte[] imageData, Integer stockQuantity, BigDecimal weightKg, String dimensions, String manufacturingLocation, Boolean shippingCarbonOffset, Boolean recyclable, Boolean biodegradable, Boolean renewableEnergyUsed, Boolean active, Boolean verified, LocalDateTime verificationDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.carbonImpact = carbonImpact;
        this.carbonCalculationMethod = carbonCalculationMethod;
        this.carbonBreakdown = carbonBreakdown;
        this.ecoCertified = ecoCertified;
        this.ecoCertificationDetails = ecoCertificationDetails;
        this.ecoRating = ecoRating;
        this.category = category;
        this.subCategory = subCategory;
        this.brand = brand;
        this.imageData = imageData;
        this.stockQuantity = stockQuantity;
        this.weightKg = weightKg;
        this.dimensions = dimensions;
        this.manufacturingLocation = manufacturingLocation;
        this.shippingCarbonOffset = shippingCarbonOffset;
        this.recyclable = recyclable;
        this.biodegradable = biodegradable;
        this.renewableEnergyUsed = renewableEnergyUsed;
        this.active = active;
        this.verified = verified;
        this.verificationDate = verificationDate;
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

    public BigDecimal getCarbonImpact() { return carbonImpact; }
    public void setCarbonImpact(BigDecimal carbonImpact) { this.carbonImpact = carbonImpact; }

    public CarbonCalculationMethod getCarbonCalculationMethod() { return carbonCalculationMethod; }
    public void setCarbonCalculationMethod(CarbonCalculationMethod carbonCalculationMethod) { this.carbonCalculationMethod = carbonCalculationMethod; }

    public String getCarbonBreakdown() { return carbonBreakdown; }
    public void setCarbonBreakdown(String carbonBreakdown) { this.carbonBreakdown = carbonBreakdown; }

    public Boolean getEcoCertified() { return ecoCertified; }
    public void setEcoCertified(Boolean ecoCertified) { this.ecoCertified = ecoCertified; }

    public String getEcoCertificationDetails() { return ecoCertificationDetails; }
    public void setEcoCertificationDetails(String ecoCertificationDetails) { this.ecoCertificationDetails = ecoCertificationDetails; }

    public BigDecimal getEcoRating() { return ecoRating; }
    public void setEcoRating(BigDecimal ecoRating) { this.ecoRating = ecoRating; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSubCategory() { return subCategory; }
    public void setSubCategory(String subCategory) { this.subCategory = subCategory; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public byte[] getImageData() { return imageData; }
    public void setImageData(byte[] imageData) { this.imageData = imageData; }

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }

    public BigDecimal getWeightKg() { return weightKg; }
    public void setWeightKg(BigDecimal weightKg) { this.weightKg = weightKg; }

    public String getDimensions() { return dimensions; }
    public void setDimensions(String dimensions) { this.dimensions = dimensions; }

    public String getManufacturingLocation() { return manufacturingLocation; }
    public void setManufacturingLocation(String manufacturingLocation) { this.manufacturingLocation = manufacturingLocation; }

    public Boolean getShippingCarbonOffset() { return shippingCarbonOffset; }
    public void setShippingCarbonOffset(Boolean shippingCarbonOffset) { this.shippingCarbonOffset = shippingCarbonOffset; }

    public Boolean getRecyclable() { return recyclable; }
    public void setRecyclable(Boolean recyclable) { this.recyclable = recyclable; }

    public Boolean getBiodegradable() { return biodegradable; }
    public void setBiodegradable(Boolean biodegradable) { this.biodegradable = biodegradable; }

    public Boolean getRenewableEnergyUsed() { return renewableEnergyUsed; }
    public void setRenewableEnergyUsed(Boolean renewableEnergyUsed) { this.renewableEnergyUsed = renewableEnergyUsed; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public Boolean getVerified() { return verified; }
    public void setVerified(Boolean verified) { this.verified = verified; }

    public LocalDateTime getVerificationDate() { return verificationDate; }
    public void setVerificationDate(LocalDateTime verificationDate) { this.verificationDate = verificationDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Helper method to calculate eco-score
    @Transient
    public String getEcoLabel() {
        if (carbonImpact == null) return "UNKNOWN";
        double impact = carbonImpact.doubleValue();
        if (impact < 1.0) return "EXCELLENT";
        if (impact < 5.0) return "GOOD";
        if (impact < 10.0) return "MODERATE";
        if (impact < 20.0) return "POOR";
        return "HIGH_IMPACT";
    }

    @Transient
    public String getEcoBadgeColor() {
        String label = getEcoLabel();
        switch (label) {
            case "EXCELLENT": return "#059669";
            case "GOOD": return "#10b981";
            case "MODERATE": return "#f59e0b";
            case "POOR": return "#f97316";
            case "HIGH_IMPACT": return "#ef4444";
            default: return "#6b7280";
        }
    }

    // Manual builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Long sellerId;
        private String sellerName;
        private BigDecimal carbonImpact;
        private CarbonCalculationMethod carbonCalculationMethod;
        private String carbonBreakdown;
        private Boolean ecoCertified = false;
        private String ecoCertificationDetails;
        private BigDecimal ecoRating;
        private String category;
        private String subCategory;
        private String brand;
        private byte[] imageData; // Changed from imageUrl
        private Integer stockQuantity = 0;
        private BigDecimal weightKg;
        private String dimensions;
        private String manufacturingLocation;
        private Boolean shippingCarbonOffset = false;
        private Boolean recyclable = false;
        private Boolean biodegradable = false;
        private Boolean renewableEnergyUsed = false;
        private Boolean active = true;
        private Boolean verified = false;
        private LocalDateTime verificationDate;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder price(BigDecimal price) { this.price = price; return this; }
        public Builder sellerId(Long sellerId) { this.sellerId = sellerId; return this; }
        public Builder sellerName(String sellerName) { this.sellerName = sellerName; return this; }
        public Builder carbonImpact(BigDecimal carbonImpact) { this.carbonImpact = carbonImpact; return this; }
        public Builder carbonCalculationMethod(CarbonCalculationMethod carbonCalculationMethod) { this.carbonCalculationMethod = carbonCalculationMethod; return this; }
        public Builder carbonBreakdown(String carbonBreakdown) { this.carbonBreakdown = carbonBreakdown; return this; }
        public Builder ecoCertified(Boolean ecoCertified) { this.ecoCertified = ecoCertified; return this; }
        public Builder ecoCertificationDetails(String ecoCertificationDetails) { this.ecoCertificationDetails = ecoCertificationDetails; return this; }
        public Builder ecoRating(BigDecimal ecoRating) { this.ecoRating = ecoRating; return this; }
        public Builder category(String category) { this.category = category; return this; }
        public Builder subCategory(String subCategory) { this.subCategory = subCategory; return this; }
        public Builder brand(String brand) { this.brand = brand; return this; }
        public Builder imageData(byte[] imageData) { this.imageData = imageData; return this; } // Changed from imageUrl
        public Builder stockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; return this; }
        public Builder weightKg(BigDecimal weightKg) { this.weightKg = weightKg; return this; }
        public Builder dimensions(String dimensions) { this.dimensions = dimensions; return this; }
        public Builder manufacturingLocation(String manufacturingLocation) { this.manufacturingLocation = manufacturingLocation; return this; }
        public Builder shippingCarbonOffset(Boolean shippingCarbonOffset) { this.shippingCarbonOffset = shippingCarbonOffset; return this; }
        public Builder recyclable(Boolean recyclable) { this.recyclable = recyclable; return this; }
        public Builder biodegradable(Boolean biodegradable) { this.biodegradable = biodegradable; return this; }
        public Builder renewableEnergyUsed(Boolean renewableEnergyUsed) { this.renewableEnergyUsed = renewableEnergyUsed; return this; }
        public Builder active(Boolean active) { this.active = active; return this; }
        public Builder verified(Boolean verified) { this.verified = verified; return this; }
        public Builder verificationDate(LocalDateTime verificationDate) { this.verificationDate = verificationDate; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Product build() {
            return new Product(id, name, description, price, sellerId, sellerName, carbonImpact, carbonCalculationMethod, carbonBreakdown, ecoCertified, ecoCertificationDetails, ecoRating, category, subCategory, brand, imageData, stockQuantity, weightKg, dimensions, manufacturingLocation, shippingCarbonOffset, recyclable, biodegradable, renewableEnergyUsed, active, verified, verificationDate, createdAt, updatedAt);
        }
    }
}