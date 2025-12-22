package com.example.EcoBazaar_module2.controller;

import com.example.EcoBazaar_module2.model.Order;
import com.example.EcoBazaar_module2.model.OrderItem;
import com.example.EcoBazaar_module2.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> createOrder(@PathVariable Long userId) {
        try {
            Order order = orderService.createOrderFromCart(userId);

            Map<String, Object> response = new HashMap<>();
            response.put("orderId", order.getId());
            response.put("totalAmount", order.getTotalAmount());
            response.put("totalCarbonFootprint", order.getTotalCarbonFootprint());
            response.put("message", "Order created successfully");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Get user's orders
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getUserOrders(@PathVariable Long userId) {
        List<Order> orders = orderService.getUserOrders(userId);

        return ResponseEntity.ok(orders.stream().map(order -> {
            Map<String, Object> dto = new HashMap<>();
            dto.put("id", order.getId());
            dto.put("totalAmount", order.getTotalAmount());
            dto.put("totalCarbonFootprint", order.getTotalCarbonFootprint());
            dto.put("status", order.getStatus());
            dto.put("createdAt", order.getCreatedAt());
            dto.put("itemCount", order.getItems().size());
            return dto;
        }).collect(Collectors.toList()));
    }

    /**
     * Get user's orders with filtering and sorting
     *
     * Query Parameters:
     * - category: Filter by product category
     * - minPrice, maxPrice: Total order amount range
     * - minCarbon, maxCarbon: Total carbon footprint range
     * - status: Filter by order status (CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED)
     * - sortBy: date_asc, date_desc, amount_asc, amount_desc, carbon_asc, carbon_desc
     */
    @GetMapping("/user/{userId}/filtered")
    public ResponseEntity<List<Map<String, Object>>> getFilteredUserOrders(
            @PathVariable Long userId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minCarbon,
            @RequestParam(required = false) Double maxCarbon,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String sortBy
    ) {
        List<Order> orders = orderService.getUserOrders(userId);

        // Apply filters
        List<Order> filteredOrders = orders.stream()
                .filter(order -> {
                    // Status filter
                    if (status != null && !status.isEmpty()) {
                        if (!order.getStatus().toString().equals(status)) return false;
                    }

                    // Amount filter
                    if (minPrice != null && order.getTotalAmount() < minPrice) return false;
                    if (maxPrice != null && order.getTotalAmount() > maxPrice) return false;

                    // Carbon filter
                    if (minCarbon != null && order.getTotalCarbonFootprint() < minCarbon) return false;
                    if (maxCarbon != null && order.getTotalCarbonFootprint() > maxCarbon) return false;

                    // Category filter (check if any item in order matches category)
                    if (category != null && !category.isEmpty() && !category.equals("All")) {
                        boolean hasCategory = order.getItems().stream()
                                .anyMatch(item -> item.getProduct().getCategory().equals(category));
                        if (!hasCategory) return false;
                    }

                    return true;
                })
                .collect(Collectors.toList());

        // Apply sorting
        if (sortBy != null) {
            filteredOrders = applySorting(filteredOrders, sortBy);
        }

        return ResponseEntity.ok(filteredOrders.stream().map(order -> {
            Map<String, Object> dto = new HashMap<>();
            dto.put("id", order.getId());
            dto.put("totalAmount", order.getTotalAmount());
            dto.put("totalCarbonFootprint", order.getTotalCarbonFootprint());
            dto.put("status", order.getStatus());
            dto.put("createdAt", order.getCreatedAt());
            dto.put("itemCount", order.getItems().size());
            return dto;
        }).collect(Collectors.toList()));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Map<String, Object>> getOrderDetails(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);

        Map<String, Object> dto = new HashMap<>();
        dto.put("id", order.getId());
        dto.put("totalAmount", order.getTotalAmount());
        dto.put("totalCarbonFootprint", order.getTotalCarbonFootprint());
        dto.put("status", order.getStatus());
        dto.put("createdAt", order.getCreatedAt());
        dto.put("items", order.getItems().stream().map(item -> {
            Map<String, Object> itemDTO = new HashMap<>();
            itemDTO.put("productName", item.getProductNameSnapshot());
            itemDTO.put("quantity", item.getQuantity());
            itemDTO.put("price", item.getPriceSnapshot());
            itemDTO.put("carbon", item.getCarbonSnapshot());
            itemDTO.put("category", item.getProduct().getCategory());
            return itemDTO;
        }).collect(Collectors.toList()));

        return ResponseEntity.ok(dto);
    }

    /**
     * Get all orders with filtering (Admin)
     */
    @GetMapping("/admin/all")
    public ResponseEntity<List<Map<String, Object>>> getAllOrders(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minCarbon,
            @RequestParam(required = false) Double maxCarbon,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String sortBy
    ) {
        List<Order> orders = orderService.getAllOrders();

        // Apply filters
        List<Order> filteredOrders = orders.stream()
                .filter(order -> {
                    // Status filter
                    if (status != null && !status.isEmpty()) {
                        if (!order.getStatus().toString().equals(status)) return false;
                    }

                    // Amount filter
                    if (minPrice != null && order.getTotalAmount() < minPrice) return false;
                    if (maxPrice != null && order.getTotalAmount() > maxPrice) return false;

                    // Carbon filter
                    if (minCarbon != null && order.getTotalCarbonFootprint() < minCarbon) return false;
                    if (maxCarbon != null && order.getTotalCarbonFootprint() > maxCarbon) return false;

                    // Category filter
                    if (category != null && !category.isEmpty() && !category.equals("All")) {
                        boolean hasCategory = order.getItems().stream()
                                .anyMatch(item -> item.getProduct().getCategory().equals(category));
                        if (!hasCategory) return false;
                    }

                    return true;
                })
                .collect(Collectors.toList());

        // Apply sorting
        if (sortBy != null) {
            filteredOrders = applySorting(filteredOrders, sortBy);
        }

        return ResponseEntity.ok(filteredOrders.stream().map(order -> {
            Map<String, Object> dto = new HashMap<>();
            dto.put("id", order.getId());
            dto.put("userId", order.getUser().getId());
            dto.put("userEmail", order.getUser().getEmail());
            dto.put("totalAmount", order.getTotalAmount());
            dto.put("totalCarbonFootprint", order.getTotalCarbonFootprint());
            dto.put("status", order.getStatus());
            dto.put("createdAt", order.getCreatedAt());
            return dto;
        }).collect(Collectors.toList()));
    }

    private List<Order> applySorting(List<Order> orders, String sortBy) {
        Comparator<Order> comparator = null;

        switch (sortBy) {
            case "date_asc":
                comparator = Comparator.comparing(Order::getCreatedAt);
                break;
            case "date_desc":
                comparator = Comparator.comparing(Order::getCreatedAt).reversed();
                break;
            case "amount_asc":
                comparator = Comparator.comparing(Order::getTotalAmount);
                break;
            case "amount_desc":
                comparator = Comparator.comparing(Order::getTotalAmount).reversed();
                break;
            case "carbon_asc":
                comparator = Comparator.comparing(Order::getTotalCarbonFootprint);
                break;
            case "carbon_desc":
                comparator = Comparator.comparing(Order::getTotalCarbonFootprint).reversed();
                break;
            default:
                comparator = Comparator.comparing(Order::getCreatedAt).reversed();
        }

        return orders.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}