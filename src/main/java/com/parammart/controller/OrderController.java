package com.parammart.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.parammart.dto.request.PlaceOrderRequest;
import com.parammart.dto.response.ApiResponse;
import com.parammart.dto.response.OrderResponse;
import com.parammart.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/place")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<OrderResponse>> placeOrder(
            @Valid @RequestBody PlaceOrderRequest request) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Order placed successfully.",
                        service.placeOrder(request)
                )
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrders() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Orders fetched successfully.",
                        service.getMyOrders()
                )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrder(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Order fetched successfully.",
                        service.getOrderById(id)
                )
        );
    }

    @PutMapping("/cancel/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<OrderResponse>> cancelOrder(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Order cancelled successfully.",
                        service.cancelOrder(id)
                )
        );
    }
}