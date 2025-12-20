package com.example.EcoBazaar_module2.controller;

import com.example.EcoBazaar_module2.model.Product;
import com.example.EcoBazaar_module2.model.ProductCarbonData;
import com.example.EcoBazaar_module2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllProducts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double maxCarbon
    ) {
        List<Product> products;
        if (search == null && category == null && minPrice == null && maxPrice == null && maxCarbon == null) {
            products = productService.getAllVerifiedProducts();
        } else {
            products = productService.searchProducts(search, category, minPrice, maxPrice, maxCarbon);
        }

        return ResponseEntity.ok(products.stream()
                .map(this::toProductDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(toProductDTO(product));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Map<String, Object> request) {
        try {
            Long userId = Long.valueOf(request.get("userId").toString());
            String name = request.get("name").toString();
            String description = request.get("description").toString();
            Double price = Double.valueOf(request.get("price").toString());
            Integer quantity = Integer.valueOf(request.getOrDefault("quantity", 1).toString());
            String category = request.get("category").toString();
            String imageUrl = request.getOrDefault("imageUrl", "").toString();
            String imagePath = request.getOrDefault("imagePath", "").toString();

            ProductCarbonData carbonData = new ProductCarbonData();
            carbonData.setManufacturing(Double.valueOf(request.getOrDefault("manufacturing", 0.0).toString()));
            carbonData.setTransportation(Double.valueOf(request.getOrDefault("transportation", 0.0).toString()));
            carbonData.setPackaging(Double.valueOf(request.getOrDefault("packaging", 0.0).toString()));
            carbonData.setUsage(Double.valueOf(request.getOrDefault("usage", 0.0).toString()));
            carbonData.setDisposal(Double.valueOf(request.getOrDefault("disposal", 0.0).toString()));

            Product product = productService.createProduct(userId, name, description, price, quantity,
                    category, imageUrl, imagePath, carbonData);

            return ResponseEntity.ok(toProductDTO(product));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            Long userId = Long.valueOf(request.get("userId").toString());
            String name = request.get("name").toString();
            String description = request.get("description").toString();
            Double price = Double.valueOf(request.get("price").toString());
            Integer quantity = Integer.valueOf(request.getOrDefault("quantity", 1).toString());
            String category = request.get("category").toString();
            String imageUrl = request.getOrDefault("imageUrl", "").toString();
            String imagePath = request.getOrDefault("imagePath", "").toString();

            ProductCarbonData carbonData = new ProductCarbonData();
            carbonData.setManufacturing(Double.valueOf(request.getOrDefault("manufacturing", 0.0).toString()));
            carbonData.setTransportation(Double.valueOf(request.getOrDefault("transportation", 0.0).toString()));
            carbonData.setPackaging(Double.valueOf(request.getOrDefault("packaging", 0.0).toString()));
            carbonData.setUsage(Double.valueOf(request.getOrDefault("usage", 0.0).toString()));
            carbonData.setDisposal(Double.valueOf(request.getOrDefault("disposal", 0.0).toString()));

            Product product = productService.updateProduct(userId, id, name, description, price, quantity,
                    category, imageUrl, imagePath, carbonData);

            return ResponseEntity.ok(toProductDTO(product));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id, @RequestParam Long userId) {
        try {
            productService.deleteProduct(userId, id);
            return ResponseEntity.ok(Map.of("message", "Product deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(403).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<Map<String, Object>>> getSellerProducts(@PathVariable Long sellerId) {
        List<Product> products = productService.getSellerProducts(sellerId);
        return ResponseEntity.ok(products.stream()
                .map(this::toProductDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/admin/pending")
    public ResponseEntity<List<Map<String, Object>>> getPendingProducts() {
        List<Product> products = productService.getPendingProducts();
        return ResponseEntity.ok(products.stream()
                .map(this::toProductDTO)
                .collect(Collectors.toList()));
    }

    @PutMapping("/admin/verify/{id}")
    public ResponseEntity<?> verifyProduct(@PathVariable Long id, @RequestParam Long adminId) {
        try {
            productService.verifyProduct(adminId, id);
            return ResponseEntity.ok(Map.of("message", "Product verified"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    private Map<String, Object> toProductDTO(Product product) {
        Map<String, Object> dto = new HashMap<>();
        dto.put("id", product.getId());
        dto.put("name", product.getName());
        dto.put("description", product.getDescription());
        dto.put("price", product.getPrice());
        dto.put("quantity", product.getQuantity());

        // Prefer served image path over generic URL if available
        if (product.getImagePath() != null && !product.getImagePath().isEmpty()) {
            dto.put("imageUrl", "/api/images/" + product.getImagePath());
        } else {
            dto.put("imageUrl", product.getImageUrl());
        }

        dto.put("category", product.getCategory());
        dto.put("carbonFootprint", product.getTotalCarbonFootprint());
        dto.put("ecoRating", product.getEcoRating());
        dto.put("verified", product.isVerified());
        dto.put("sellerId", product.getSeller().getId());
        dto.put("sellerName", product.getSeller().getFullName());

        if (product.getCarbonData() != null) {
            Map<String, Double> breakdown = new HashMap<>();
            breakdown.put("manufacturing", product.getCarbonData().getManufacturing());
            breakdown.put("transportation", product.getCarbonData().getTransportation());
            breakdown.put("packaging", product.getCarbonData().getPackaging());
            breakdown.put("usage", product.getCarbonData().getUsage());
            breakdown.put("disposal", product.getCarbonData().getDisposal());
            dto.put("carbonBreakdown", breakdown);
        }

        return dto;
    }
}