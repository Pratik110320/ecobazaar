package com.example.EcoBazaar_module2.controller;


import com.example.EcoBazaar_module2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

interface UserRepoDashboard extends JpaRepository<User, Long> {}

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private UserRepoDashboard userRepo;

    // Module 4: Get User Impact Stats
    @GetMapping("/{userId}")
    public ResponseEntity<DashboardStats> getUserStats(@PathVariable Long userId) {
        return userRepo.findById(userId)
                .map(user -> {
                    DashboardStats stats = new DashboardStats();
                    stats.setTotalCarbonSaved(user.getTotalCarbonSaved());
                    stats.setEcoScore(user.getEcoScore());
                    stats.setGreenPurchases(user.getGreenPurchasesCount());

                    // Logic for badges
                    if (user.getEcoScore() > 1000) stats.setBadge("Planet Protector");
                    else if (user.getEcoScore() > 500) stats.setBadge("Low Carbon Leader");
                    else stats.setBadge("Eco Starter");

                    return ResponseEntity.ok(stats);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DTO
    static class DashboardStats {
        private Double totalCarbonSaved;
        private Integer ecoScore;
        private Integer greenPurchases;
        private String badge;

        // Getters & Setters (Lombok would do this automatically, writing out for clarity)
        public Double getTotalCarbonSaved() { return totalCarbonSaved; }
        public void setTotalCarbonSaved(Double t) { this.totalCarbonSaved = t; }
        public Integer getEcoScore() { return ecoScore; }
        public void setEcoScore(Integer e) { this.ecoScore = e; }
        public Integer getGreenPurchases() { return greenPurchases; }
        public void setGreenPurchases(Integer g) { this.greenPurchases = g; }
        public String getBadge() { return badge; }
        public void setBadge(String b) { this.badge = b; }
    }
}