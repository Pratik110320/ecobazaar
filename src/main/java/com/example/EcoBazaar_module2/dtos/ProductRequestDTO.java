package com.example.EcoBazaar_module2.dtos;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductRequestDTO {

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 200)
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @NotNull(message = "Seller ID is required")
    private Long sellerId;

    private String sellerName;

    private String category;
    private String subCategory;
    private String brand;
    // imageUrl removed, will be handled by MultipartFile

    @Min(value = 0)
    private Integer stockQuantity;

    @DecimalMin(value = "0.0")
    private BigDecimal weightKg;

    private String dimensions;
    private String manufacturingLocation;

    // Carbon data (optional - can be auto-calculated)
    @DecimalMin(value = "0.0")
    private BigDecimal carbonImpact;

    // Eco features
    private Boolean ecoCertified = false;
    private String ecoCertificationDetails;
    private Boolean recyclable = false;
    private Boolean biodegradable = false;
    private Boolean renewableEnergyUsed = false;
    private Boolean shippingCarbonOffset = false;

    public ProductRequestDTO() {}

    // Updated constructor
    public ProductRequestDTO(String name, String description, BigDecimal price, Long sellerId, String sellerName, String category, String subCategory, String brand, Integer stockQuantity, BigDecimal weightKg, String dimensions, String manufacturingLocation, BigDecimal carbonImpact, Boolean ecoCertified, String ecoCertificationDetails, Boolean recyclable, Boolean biodegradable, Boolean renewableEnergyUsed, Boolean shippingCarbonOffset) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.category = category;
        this.subCategory = subCategory;
        this.brand = brand;
        this.stockQuantity = stockQuantity;
        this.weightKg = weightKg;
        this.dimensions = dimensions;
        this.manufacturingLocation = manufacturingLocation;
        this.carbonImpact = carbonImpact;
        this.ecoCertified = ecoCertified;
        this.ecoCertificationDetails = ecoCertificationDetails;
        this.recyclable = recyclable;
        this.biodegradable = biodegradable;
        this.renewableEnergyUsed = renewableEnergyUsed;
        this.shippingCarbonOffset = shippingCarbonOffset;
    }

    // Getters & Setters
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

    // imageUrl getter/setter removed

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

    public Boolean getEcoCertified() { return ecoCertified; }
    public void setEcoCertified(Boolean ecoCertified) { this.ecoCertified = ecoCertified; }

    public String getEcoCertificationDetails() { return ecoCertificationDetails; }
    public void setEcoCertificationDetails(String ecoCertificationDetails) { this.ecoCertificationDetails = ecoCertificationDetails; }

    public Boolean getRecyclable() { return recyclable; }
    public void setRecyclable(Boolean recyclable) { this.recyclable = recyclable; }

    public Boolean getBiodegradable() { return biodegradable; }
    public void setBiodegradable(Boolean biodegradable) { this.biodegradable = biodegradable; }

    public Boolean getRenewableEnergyUsed() { return renewableEnergyUsed; }
    public void setRenewableEnergyUsed(Boolean renewableEnergyUsed) { this.renewableEnergyUsed = renewableEnergyUsed; }

    public Boolean getShippingCarbonOffset() { return shippingCarbonOffset; }
    public void setShippingCarbonOffset(Boolean shippingCarbonOffset) { this.shippingCarbonOffset = shippingCarbonOffset; }

    // Manual builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String name;
        private String description;
        private BigDecimal price;
        private Long sellerId;
        private String sellerName;
        private String category;
        private String subCategory;
        private String brand;
        // imageUrl removed
        private Integer stockQuantity;
        private BigDecimal weightKg;
        private String dimensions;
        private String manufacturingLocation;
        private BigDecimal carbonImpact;
        private Boolean ecoCertified = false;
        private String ecoCertificationDetails;
        private Boolean recyclable = false;
        private Boolean biodegradable = false;
        private Boolean renewableEnergyUsed = false;
        private Boolean shippingCarbonOffset = false;

        public Builder name(String name) { this.name = name; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder price(BigDecimal price) { this.price = price; return this; }
        public Builder sellerId(Long sellerId) { this.sellerId = sellerId; return this; }
        public Builder sellerName(String sellerName) { this.sellerName = sellerName; return this; }
        public Builder category(String category) { this.category = category; return this; }
        public Builder subCategory(String subCategory) { this.subCategory = subCategory; return this; }
        public Builder brand(String brand) { this.brand = brand; return this; }
        // imageUrl removed
        public Builder stockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; return this; }
        public Builder weightKg(BigDecimal weightKg) { this.weightKg = weightKg; return this; }
        public Builder dimensions(String dimensions) { this.dimensions = dimensions; return this; }
        public Builder manufacturingLocation(String manufacturingLocation) { this.manufacturingLocation = manufacturingLocation; return this; }
        public Builder carbonImpact(BigDecimal carbonImpact) { this.carbonImpact = carbonImpact; return this; }
        public Builder ecoCertified(Boolean ecoCertified) { this.ecoCertified = ecoCertified; return this; }
        public Builder ecoCertificationDetails(String ecoCertificationDetails) { this.ecoCertificationDetails = ecoCertificationDetails; return this; }
        public Builder recyclable(Boolean recyclable) { this.recyclable = recyclable; return this; }
        public Builder biodegradable(Boolean biodegradable) { this.biodegradable = biodegradable; return this; }
        public Builder renewableEnergyUsed(Boolean renewableEnergyUsed) { this.renewableEnergyUsed = renewableEnergyUsed; return this; }
        public Builder shippingCarbonOffset(Boolean shippingCarbonOffset) { this.shippingCarbonOffset = shippingCarbonOffset; return this; }

        public ProductRequestDTO build() {
            return new ProductRequestDTO(
                    name, description, price, sellerId, sellerName,
                    category, subCategory, brand, stockQuantity,
                    weightKg, dimensions, manufacturingLocation, carbonImpact,
                    ecoCertified, ecoCertificationDetails, recyclable, biodegradable, renewableEnergyUsed, shippingCarbonOffset
            );
        }
    }
}