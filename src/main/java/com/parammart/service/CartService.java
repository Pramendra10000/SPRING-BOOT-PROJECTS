package com.parammart.service;

import com.parammart.dto.request.CartRequest;
import com.parammart.entity.Cart;

public interface CartService {

    Cart addToCart(CartRequest request);

    Cart getMyCart();

    Cart updateQuantity(Long productId,Integer quantity);

    void removeItem(Long productId);

    void clearCart();

}