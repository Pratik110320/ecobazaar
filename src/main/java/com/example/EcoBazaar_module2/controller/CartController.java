package com.example.EcoBazaar_module2.controller;


import com.example.EcoBazaar_module2.model.Product;
import com.example.EcoBazaar_module2.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
}
interface UserRepo extends JpaRepository<User, Long> {}

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    // Module 3: Checkout & Impact Calculation
    // In a real app, we'd have a persistent 'Cart' entity.
    // Here we simulate checkout with a list of product IDs.
    @PostMapping("/checkout/{userId}")
    public CartSummary checkout(@PathVariable Long userId, @RequestBody List<Long> productIds) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        double totalCost = 0;
        double totalCarbon = 0;
        double potentialCarbonSavings = 0;
        List<String> suggestions = new ArrayList<>();

        for (Long pid : productIds) {
            Product p = productRepo.findById(pid).orElse(null);
            if (p != null) {
                totalCost += p.getPrice();
                totalCarbon += p.getCarbonFootprint();

                // Module 3 Logic: Suggest Greener Alternative
                if (!p.isEcoFriendly()) {
                    List<Product> alternatives = productRepo.findByCategory(p.getCategory());
                    // Find an alternative with significantly lower CO2
                    Optional<Product> better = alternatives.stream()
                            .filter(alt -> alt.getCarbonFootprint() < p.getCarbonFootprint())
                            .findFirst();

                    if (better.isPresent()) {
                        double saving = p.getCarbonFootprint() - better.get().getCarbonFootprint();
                        suggestions.add("Switch " + p.getName() + " to " + better.get().getName() + " to save " + String.format("%.2f", saving) + "kg CO2");
                        potentialCarbonSavings += saving;
                    }
                } else {
                    // If they bought green, they save impact compared to "average" (mock logic)
                    user.addCarbonSaved(1.5); // Reward for buying green
                }
            }
        }

        userRepo.save(user); // Update user stats (Module 4)

        return new CartSummary(totalCost, totalCarbon, suggestions, potentialCarbonSavings);
    }

    // DTO for Cart Response
    static class CartSummary {
        public double totalCost;
        public double totalCarbon;
        public List<String> ecoSuggestions;
        public double potentialSavings;

        public CartSummary(double c, double co2, List<String> s, double ps) {
            this.totalCost = c;
            this.totalCarbon = co2;
            this.ecoSuggestions = s;
            this.potentialSavings = ps;
        }
    }
}