package com.parammart.service;

import com.parammart.dto.request.CartRequest;
import com.parammart.dto.response.CartResponse;

public interface CartService {

    CartResponse addToCart(CartRequest request);

    CartResponse getMyCart();

    CartResponse updateQuantity(Long productId, Integer quantity);

    void removeItem(Long productId);

    void clearCart();
}