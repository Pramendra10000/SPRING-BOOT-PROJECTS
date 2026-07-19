package com.parammart.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.parammart.dto.request.CartRequest;
import com.parammart.entity.Cart;
import com.parammart.entity.CartItem;
import com.parammart.entity.Product;
import com.parammart.entity.User;
import com.parammart.exception.BadRequestException;
import com.parammart.exception.ResourceNotFoundException;
import com.parammart.repository.CartItemRepository;
import com.parammart.repository.CartRepository;
import com.parammart.repository.ProductRepository;
import com.parammart.repository.UserRepository;
import com.parammart.service.CartService;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository,
            UserRepository userRepository) {

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Cart addToCart(CartRequest request) {

        User user = getLoggedInUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> createCart(user));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        Optional<CartItem> optionalItem =
                cartItemRepository.findByCartIdAndProductId(
                        cart.getId(),
                        product.getId());

        CartItem item;

        if (optionalItem.isPresent()) {

            item = optionalItem.get();

            item.setQuantity(
                    item.getQuantity() + request.getQuantity());

        } else {

            item = new CartItem();

            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(request.getQuantity());

            // Replace getPrice() with getSellingPrice() if needed
            item.setPrice(product.getPrice());

        }

        item.setTotalPrice(
                item.getPrice().multiply(
                        BigDecimal.valueOf(item.getQuantity())));

        cartItemRepository.save(item);

        calculateCart(cart);

        return cartRepository.save(cart);
    }

    @Override
    public Cart getMyCart() {

        User user = getLoggedInUser();

        return cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));
    }

    @Override
    public Cart updateQuantity(Long productId, Integer quantity) {

        Cart cart = getMyCart();

        CartItem item =
                cartItemRepository
                        .findByCartIdAndProductId(cart.getId(), productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Item not found"));

        item.setQuantity(quantity);

        item.setTotalPrice(
                item.getPrice()
                        .multiply(BigDecimal.valueOf(quantity)));

        cartItemRepository.save(item);

        calculateCart(cart);

        return cartRepository.save(cart);
    }

    @Override
    public void removeItem(Long productId) {

        Cart cart = getMyCart();

        CartItem item =
                cartItemRepository
                        .findByCartIdAndProductId(cart.getId(), productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Item not found"));

        cartItemRepository.delete(item);

        calculateCart(cart);

        cartRepository.save(cart);
    }

    @Override
    public void clearCart() {

        Cart cart = getMyCart();

        cart.getCartItems().clear();

        cart.setTotalAmount(BigDecimal.ZERO);

        cart.setTotalItems(0);

        cartRepository.save(cart);
    }

    // =============================

    private Cart createCart(User user) {

        Cart cart = new Cart();

        cart.setUser(user);

        return cartRepository.save(cart);
    }

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    private void calculateCart(Cart cart) {

        BigDecimal total = BigDecimal.ZERO;

        int totalItems = 0;

        for (CartItem item : cart.getCartItems()) {

            total = total.add(item.getTotalPrice());

            totalItems += item.getQuantity();
        }

        cart.setTotalAmount(total);

        cart.setTotalItems(totalItems);
    }

}