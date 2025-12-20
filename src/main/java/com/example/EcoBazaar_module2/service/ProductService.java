package com.example.EcoBazaar_module2.service;

import com.example.EcoBazaar_module2.model.Product;
import com.example.EcoBazaar_module2.model.ProductCarbonData;
import com.example.EcoBazaar_module2.model.User;
import com.example.EcoBazaar_module2.model.Role;
import com.example.EcoBazaar_module2.repository.ProductCarbonDataRepository;
import com.example.EcoBazaar_module2.repository.ProductRepository;
import com.example.EcoBazaar_module2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCarbonDataRepository carbonDataRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditService auditService;

    public List<Product> getAllVerifiedProducts() {
        return productRepository.findByVerifiedTrue();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<Product> searchProducts(String name, String category, Double minPrice, Double maxPrice, Double maxCarbon) {
        List<Product> products = productRepository.searchProducts(
                (name != null && !name.isEmpty()) ? name : null,
                (category != null && !category.equals("All")) ? category : null,
                minPrice,
                maxPrice
        );

        if (maxCarbon != null) {
            products = products.stream()
                    .filter(p -> p.getTotalCarbonFootprint() <= maxCarbon)
                    .collect(Collectors.toList());
        }

        return products;
    }

    @Transactional
    public Product createProduct(Long sellerId, String name, String description, Double price, Integer quantity,
                                 String category, String imageUrl, String imagePath, ProductCarbonData carbonData) {
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        if (seller.getRole() != Role.SELLER && seller.getRole() != Role.ADMIN) {
            throw new RuntimeException("Unauthorized: Only Sellers or Admins can add products");
        }

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setCategory(category);
        product.setImageUrl(imageUrl);
        product.setImagePath(imagePath);
        product.setSeller(seller);
        product.setActive(true);
        product.setVerified(seller.getRole() == Role.ADMIN);

        Product savedProduct = productRepository.save(product);

        if (isCarbonDataEmpty(carbonData)) {
            calculateAutomaticCarbon(carbonData, category, price);
        }

        carbonData.setProduct(savedProduct);
        carbonDataRepository.save(carbonData);

        auditService.log(sellerId, "CREATE_PRODUCT", "PRODUCT", savedProduct.getId(),
                "Product: " + name);

        return savedProduct;
    }

    @Transactional
    public Product updateProduct(Long userId, Long productId, String name, String description,
                                 Double price, Integer quantity, String category, String imageUrl, String imagePath, ProductCarbonData newCarbonData) {
        Product product = getProductById(productId);
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (!product.getSeller().getId().equals(userId) && user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Unauthorized: You do not have permission to edit this product");
        }

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setCategory(category);
        product.setImageUrl(imageUrl);

        if (imagePath != null && !imagePath.isEmpty()) {
            product.setImagePath(imagePath);
        }

        if (newCarbonData != null) {
            if (user.getRole() == Role.SELLER) {
                product.setVerified(false);
            }

            ProductCarbonData existingData = product.getCarbonData();

            if (isCarbonDataEmpty(newCarbonData)) {
                calculateAutomaticCarbon(existingData, category, price);
            } else {
                existingData.setManufacturing(newCarbonData.getManufacturing());
                existingData.setTransportation(newCarbonData.getTransportation());
                existingData.setPackaging(newCarbonData.getPackaging());
                existingData.setUsage(newCarbonData.getUsage());
                existingData.setDisposal(newCarbonData.getDisposal());
            }
            carbonDataRepository.save(existingData);
        }

        Product updated = productRepository.save(product);
        auditService.log(userId, "UPDATE_PRODUCT", "PRODUCT", productId, null);

        return updated;
    }

    @Transactional
    public void deleteProduct(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = getProductById(productId);

        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Unauthorized: Only Admins can delete products");
        }

        productRepository.delete(product);
        auditService.log(userId, "DELETE_PRODUCT", "PRODUCT", productId, "Deleted by Admin");
    }

    public List<Product> getSellerProducts(Long sellerId) {
        return productRepository.findBySellerId(sellerId);
    }

    public List<Product> getPendingProducts() {
        return productRepository.findByVerifiedFalse();
    }

    @Transactional
    public void verifyProduct(Long adminId, Long productId) {
        Product product = getProductById(productId);
        product.setVerified(true);
        productRepository.save(product);

        auditService.log(adminId, "VERIFY_PRODUCT", "PRODUCT", productId, null);
    }

    private boolean isCarbonDataEmpty(ProductCarbonData data) {
        return data.getManufacturing() == 0 && data.getTransportation() == 0 &&
                data.getPackaging() == 0 && data.getUsage() == 0 && data.getDisposal() == 0;
    }

    private void calculateAutomaticCarbon(ProductCarbonData data, String category, Double price) {
        double base = 5.0;
        if (category != null) {
            switch (category.toLowerCase()) {
                case "electronics": base = 50.0; break;
                case "fashion":
                case "clothing": base = 12.0; break;
                case "home":
                case "furniture": base = 25.0; break;
                case "food": base = 3.0; break;
                case "beauty": base = 2.0; break;
                default: base = 5.0;
            }
        }
        data.setManufacturing(base * 0.6);
        data.setTransportation(base * 0.2);
        data.setPackaging(base * 0.1);
        data.setDisposal(base * 0.1);
        data.setUsage(0.0);
    }
}