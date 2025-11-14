package com.example.EcoBazaar_module2.controller;


import com.example.EcoBazaar_module2.dtos.ProductFilterDTO;
import com.example.EcoBazaar_module2.dtos.ProductRequestDTO;
import com.example.EcoBazaar_module2.dtos.ProductResponseDTO;
import com.example.EcoBazaar_module2.dtos.ProductUpdateDTO; // Import the new DTO
import com.example.EcoBazaar_module2.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Product Catalog", description = "Carbon-Aware Product Management APIs")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    /**
     * Create new product
     */
    @PostMapping(consumes = {"multipart/form-data"}) // Specify consumes
    @Operation(summary = "Create a new product with carbon footprint data")
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestPart("product") ProductRequestDTO request, // Get DTO as form part
            @RequestParam(value = "file", required = false) MultipartFile file // Get file
    ) {
        log.info("REST request to create product: {}", request.getName());
        ProductResponseDTO response = productService.createProduct(request, file); // Pass file to service
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Partially update existing product
     */
    @PatchMapping(value = "/{id}", consumes = {"multipart/form-data"}) // Changed from @PutMapping to @PatchMapping
    @Operation(summary = "Partially update an existing product")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestPart("product") ProductUpdateDTO request, // Use ProductUpdateDTO
            @RequestParam(value = "file", required = false) MultipartFile file // Get file (optional for update)
    ) {
        log.info("REST request to PATCH product with ID: {}", id);
        ProductResponseDTO response = productService.updateProduct(id, request, file); // Pass file to service
        return ResponseEntity.ok(response);
    }

    /**
     * Get product by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get product details by ID")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        log.info("REST request to get product with ID: {}", id);
        ProductResponseDTO response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all products
     */
    @GetMapping
    @Operation(summary = "Get all active products")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        log.info("REST request to get all products");
        List<ProductResponseDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Search products by keyword
     */
    @GetMapping("/search")
    @Operation(summary = "Search products by keyword in name or description")
    public ResponseEntity<List<ProductResponseDTO>> searchProducts(
            @RequestParam String keyword) {
        log.info("REST request to search products with keyword: {}", keyword);
        List<ProductResponseDTO> products = productService.searchProducts(keyword);
        return ResponseEntity.ok(products);
    }

    /**
     * Filter products with advanced criteria
     */
    @PostMapping("/filter")
    @Operation(summary = "Filter products with multiple criteria")
    public ResponseEntity<List<ProductResponseDTO>> filterProducts(
            @RequestBody ProductFilterDTO filter) {
        log.info("REST request to filter products");
        List<ProductResponseDTO> products = productService.filterProducts(filter);
        return ResponseEntity.ok(products);
    }

    /**
     * Get low carbon products
     */
    @GetMapping("/low-carbon")
    @Operation(summary = "Get products with carbon footprint below threshold")
    public ResponseEntity<List<ProductResponseDTO>> getLowCarbonProducts(
            @RequestParam(defaultValue = "5.0") BigDecimal maxImpact) {
        log.info("REST request to get low carbon products (max: {})", maxImpact);
        List<ProductResponseDTO> products = productService.getLowCarbonProducts(maxImpact);
        return ResponseEntity.ok(products);
    }

    /**
     * Get eco-certified products
     */
    @GetMapping("/eco-certified")
    @Operation(summary = "Get all eco-certified products")
    public ResponseEntity<List<ProductResponseDTO>> getEcoCertifiedProducts() {
        log.info("REST request to get eco-certified products");
        List<ProductResponseDTO> products = productService.getEcoCertifiedProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Get eco-friendly alternatives
     */
    @GetMapping("/{id}/alternatives")
    @Operation(summary = "Get eco-friendly alternatives for a product")
    public ResponseEntity<List<ProductResponseDTO>> getEcoAlternatives(
            @PathVariable Long id) {
        log.info("REST request to get alternatives for product: {}", id);
        List<ProductResponseDTO> alternatives = productService.getEcoAlternatives(id);
        return ResponseEntity.ok(alternatives);
    }

    /**
     * Delete product (soft delete)
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product (soft delete)")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.info("REST request to delete product with ID: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}