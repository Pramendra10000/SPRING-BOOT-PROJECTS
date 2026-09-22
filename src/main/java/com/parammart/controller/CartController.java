package com.parammart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parammart.dto.request.CartRequest;
import com.parammart.dto.response.ApiResponse;
import com.parammart.dto.response.CartResponse;
import com.parammart.service.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<CartResponse>> addToCart(
            @Valid @RequestBody CartRequest request) {

        CartResponse cart =
                cartService.addToCart(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                true,
                                "Product added successfully.",
                                cart
                        )
                );
    }

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<CartResponse>> getCart() {

        CartResponse cart =
                cartService.getMyCart();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cart fetched successfully.",
                        cart
                )
        );
    }

    @PutMapping("/{productId}/{quantity}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<CartResponse>> updateQuantity(
            @PathVariable Long productId,
            @PathVariable Integer quantity) {

        CartResponse cart =
                cartService.updateQuantity(
                        productId,
                        quantity
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cart updated successfully.",
                        cart
                )
        );
    }

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
                )
        );
    }

    @DeleteMapping("/clear")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<String>> clearCart() {

        cartService.clearCart();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cart cleared successfully.",
                        null
                )
        );
    }
}