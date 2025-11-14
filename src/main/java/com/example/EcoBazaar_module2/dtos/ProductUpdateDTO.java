package com.example.EcoBazaar_module2.dtos;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

// This DTO is used for PATCH requests.
// Fields are optional (no @NotBlank or @NotNull)
// to allow for partial updates.
public class ProductUpdateDTO {

    @Size(min = 2, max = 200)
    private String name;

    private String description;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    // Seller ID and Name are typically not updatable
    // private Long sellerId;
    // private String sellerName;

    private String category;
    private String subCategory;
    private String brand;

    @Min(value = 0)
    private Integer stockQuantity;

    @DecimalMin(value = "0.0")
    private BigDecimal weightKg;

    private String dimensions;
    private String manufacturingLocation;

    @DecimalMin(value = "0.0")
    private BigDecimal carbonImpact;

    private Boolean ecoCertified;
    private String ecoCertificationDetails;
    private Boolean recyclable;
    private Boolean biodegradable;
    private Boolean renewableEnergyUsed;
    private Boolean shippingCarbonOffset;

    public ProductUpdateDTO() {}

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSubCategory() { return subCategory; }
    public void setSubCategory(String subCategory) { this.subCategory = subCategory; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

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
}