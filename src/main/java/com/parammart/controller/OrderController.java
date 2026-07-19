package com.parammart.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.parammart.dto.response.ApiResponse;
import com.parammart.entity.Order;
import com.parammart.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/place")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<Order>> placeOrder() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Order placed successfully.",
                        service.placeOrder()));
    }

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<List<Order>>> getOrders() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Orders fetched successfully.",
                        service.getMyOrders()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<Order>> getOrder(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Order fetched successfully.",
                        service.getOrderById(id)));
    }

    @PutMapping("/cancel/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<Order>> cancelOrder(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Order cancelled successfully.",
                        service.cancelOrder(id)));
    }

}