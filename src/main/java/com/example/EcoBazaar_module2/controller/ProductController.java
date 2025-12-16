package com.example.EcoBazaar_module2.controller;


import com.example.EcoBazaar_module2.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
    List<Product> findByIsVerifiedTrue();
    List<Product> findByIsVerifiedFalse(); // For Admin
    List<Product> findBySellerId(Long sellerId); // For Sellers
}

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // --- PUBLIC ENDPOINTS ---

    // Module 2: Get All Verified Products (Catalog)
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findByIsVerifiedTrue();
    }

    // Module 2: Get Product Details & Carbon Breakdown
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- SELLER ENDPOINTS ---

    // Module 2: Add Product (Seller)
    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product) {
        product.setVerified(false); // Default to unverified
        calculateRating(product);
        return productRepository.save(product);
    }

    // Module 2: Seller Dashboard - View My Products
    @GetMapping("/seller/{sellerId}")
    public List<Product> getSellerProducts(@PathVariable Long sellerId) {
        return productRepository.findBySellerId(sellerId);
    }

    // Module 2: Update Product (Seller) - NO DELETE ALLOWED
    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return productRepository.findById(id).map(product -> {
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setImageUrl(productDetails.getImageUrl());
            product.setCategory(productDetails.getCategory());

            // If Carbon Footprint changes, re-trigger verification logic
            if (!product.getCarbonFootprint().equals(productDetails.getCarbonFootprint())) {
                product.setCarbonFootprint(productDetails.getCarbonFootprint());
                product.setVerified(false); // Reset verification if environmental data changes
                calculateRating(product);
            }

            Product updatedProduct = productRepository.save(product);
            return ResponseEntity.ok(updatedProduct);
        }).orElse(ResponseEntity.notFound().build());
    }

    // --- ADMIN ENDPOINTS ---

    // Module 5: Admin - Verify Product
    @PutMapping("/admin/verify/{id}")
    public ResponseEntity<?> verifyProduct(@PathVariable Long id) {
        return productRepository.findById(id).map(product -> {
            product.setVerified(true);
            productRepository.save(product);
            return ResponseEntity.ok("Product verified.");
        }).orElse(ResponseEntity.notFound().build());
    }

    // Module 5: Admin - List Pending Products
    @GetMapping("/admin/pending")
    public List<Product> getPendingProducts() {
        return productRepository.findByIsVerifiedFalse();
    }

    // Helper method for Eco Rating
    private void calculateRating(Product product) {
        if (product.getCarbonFootprint() == null) return;

        if (product.getCarbonFootprint() < 2.0) product.setEcoRating("A+");
        else if (product.getCarbonFootprint() < 5.0) product.setEcoRating("B");
        else product.setEcoRating("C");
    }
}