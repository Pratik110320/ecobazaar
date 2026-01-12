package com.example.EcoBazaar_module2.controller;

import com.example.EcoBazaar_module2.model.Product;
import com.example.EcoBazaar_module2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductComparisonController {

    @Autowired
    private ProductService productService;


    @GetMapping("/{id}/compare")
    public ResponseEntity<Map<String, Object>> compareWithAlternatives(@PathVariable Long id) {
        try {
            Product currentProduct = productService.getProductById(id);

            List<Product> alternatives = productService.getSimilarProductsByCategory(
                    currentProduct.getCategory(),
                    currentProduct.getId(),
                    5
            );

            return ResponseEntity.ok(buildComparisonResponse(currentProduct, alternatives));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Comparison failed: " + e.getMessage()));
        }
    }


    @GetMapping("/compare-manual")
    public ResponseEntity<Map<String, Object>> compareManual(@RequestParam Long id1, @RequestParam Long id2) {
        try {
            List<Product> products = productService.getProductsForComparison(id1, id2);
            Product p1 = products.get(0).getId().equals(id1) ? products.get(0) : products.get(1);
            Product p2 = products.get(0).getId().equals(id1) ? products.get(1) : products.get(0);

            // Treat p2 as the "alternative" to p1
            return ResponseEntity.ok(buildComparisonResponse(p1, List.of(p2)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Comparison failed: " + e.getMessage()));
        }
    }


    private Map<String, Object> buildComparisonResponse(Product current, List<Product> alternatives) {
        Map<String, Object> comparison = new HashMap<>();
        comparison.put("currentProduct", toProductDTO(current));
        comparison.put("alternatives", alternatives.stream().map(this::toProductDTO).collect(Collectors.toList()));
        comparison.put("carbonSavings", calculatePotentialSavings(current, alternatives));
        return comparison;
    }

    private Map<String, Object> calculatePotentialSavings(Product current, List<Product> alternatives) {
        Map<String, Object> savings = new HashMap<>();

        // Safety: Handle missing carbon data
        double currentCarbon = (current.getCarbonData() != null) ? current.getTotalCarbonFootprint() : 0.0;

        if (alternatives == null || alternatives.isEmpty()) {
            savings.put("bestSavings", 0.0);
            return savings;
        }

        Product bestAlternative = alternatives.stream()
                .filter(p -> p.getCarbonData() != null)
                .min(Comparator.comparingDouble(Product::getTotalCarbonFootprint))
                .orElse(null);

        if (bestAlternative != null && currentCarbon > 0) {
            double bestCarbon = bestAlternative.getTotalCarbonFootprint();
            if (currentCarbon > bestCarbon) {
                savings.put("bestSavings", Math.round((currentCarbon - bestCarbon) * 100.0) / 100.0);
                savings.put("bestAlternativeId", bestAlternative.getId());
                savings.put("bestAlternativeName", bestAlternative.getName());
                return savings;
            }
        }

        savings.put("bestSavings", 0.0);
        savings.put("bestAlternativeId", null);
        savings.put("bestAlternativeName", null);
        return savings;
    }

    private Map<String, Object> toProductDTO(Product product) {
        Map<String, Object> dto = new HashMap<>();
        dto.put("id", product.getId());
        dto.put("name", product.getName());
        dto.put("price", product.getPrice());
        dto.put("imageUrl", product.getImageUrl());
        dto.put("category", product.getCategory());
        // Safe access
        dto.put("carbonFootprint", (product.getCarbonData() != null)
                ? Math.round(product.getTotalCarbonFootprint() * 100.0) / 100.0
                : 0.0);
        dto.put("ecoRating", product.getEcoRating());
        dto.put("averageRating", product.getAverageRating());
        dto.put("reviewCount", product.getReviewCount());
        return dto;
    }
}