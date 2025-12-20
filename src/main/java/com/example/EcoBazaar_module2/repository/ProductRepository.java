package com.example.EcoBazaar_module2.repository;

import com.example.EcoBazaar_module2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByVerifiedTrue();

    List<Product> findByVerifiedFalse();

    List<Product> findBySellerId(Long sellerId);

    List<Product> findByCategory(String category);

    List<Product> findByActiveTrue();

    // Search query for filters
    @Query("SELECT p FROM Product p WHERE " +
            "p.verified = true AND " +
            "p.active = true AND " +
            "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:category IS NULL OR p.category = :category) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Product> searchProducts(@Param("name") String name,
                                 @Param("category") String category,
                                 @Param("minPrice") Double minPrice,
                                 @Param("maxPrice") Double maxPrice);
}