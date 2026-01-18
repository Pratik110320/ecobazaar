package com.example.EcoBazaar_module2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcoBazaarModule2Application {

	public static void main(String[] args) {
		SpringApplication.run(EcoBazaarModule2Application.class, args);

		// Log startup information
		System.out.println("=====================================");
		System.out.println("✓ EcoBazaar Backend Started Successfully");
		System.out.println("✓ Environment: " + System.getenv().getOrDefault("ENVIRONMENT", "development"));
		System.out.println("✓ Database: " + (System.getenv("DB_URL") != null ? "Connected to Neon PostgreSQL" : "Using local database"));
		System.out.println("✓ CORS Origins: " + System.getenv().getOrDefault("ALLOWED_ORIGINS", "localhost only"));
		System.out.println("=====================================");
	}

	// REMOVED: Duplicate CORS configuration
	// The CORS configuration is now handled entirely in SecurityConfig.java
	// Having multiple CORS configurations can cause conflicts
}