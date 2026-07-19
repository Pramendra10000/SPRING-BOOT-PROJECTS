package com.parammart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.parammart.dto.request.CartRequest;
import com.parammart.dto.response.ApiResponse;
import com.parammart.entity.Cart;
import com.parammart.service.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // ============================
    // Add Product To Cart
    // ============================

    @PostMapping("/add")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<Cart>> addToCart(
            @Valid @RequestBody CartRequest request) {

        Cart cart = cartService.addToCart(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "Product added successfully.",
                        cart
                ));
    }

    // ============================
    // Get Logged User Cart
    // ============================

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<Cart>> getCart() {

        Cart cart = cartService.getMyCart();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cart fetched successfully.",
                        cart
                ));
    }

    // ============================
    // Update Quantity
    // ============================

    @PutMapping("/{productId}/{quantity}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<Cart>> updateQuantity(
            @PathVariable Long productId,
            @PathVariable Integer quantity) {

        Cart cart = cartService.updateQuantity(productId, quantity);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cart updated successfully.",
                        cart
                ));
    }

    // ============================
    // Remove Item
    // ============================

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<String>> removeItem(
            @PathVariable Long productId) {

        cartService.removeItem(productId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Item removed successfully.",
                        null
                ));
    }

    // ============================
    // Clear Cart
    // ============================

    @DeleteMapping("/clear")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<String>> clearCart() {

        cartService.clearCart();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cart cleared successfully.",
                        null
                ));
    }

}