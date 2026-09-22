package com.parammart.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parammart.dto.request.CartRequest;
import com.parammart.dto.response.CartItemResponse;
import com.parammart.dto.response.CartResponse;
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
@Transactional
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

    // =========================================================
    // ADD TO CART
    // =========================================================

    @Override
    public CartResponse addToCart(CartRequest request) {

        if (request == null) {
            throw new BadRequestException("Cart request cannot be null");
        }

        if (request.getProductId() == null) {
            throw new BadRequestException("Product ID is required");
        }

        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new BadRequestException("Quantity must be greater than zero");
        }

        User user = getLoggedInUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> createCart(user));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: "
                                        + request.getProductId()));

        if (!Boolean.TRUE.equals(product.getActive())) {
            throw new BadRequestException("Product is not active");
        }

        if (product.getStock() == null || product.getStock() <= 0) {
            throw new BadRequestException("Product is out of stock");
        }

        if (request.getQuantity() > product.getStock()) {
            throw new BadRequestException(
                    "Only "
                            + product.getStock()
                            + " items are available in stock");
        }

        Optional<CartItem> optionalItem =
                cartItemRepository.findByCartIdAndProductId(
                        cart.getId(),
                        product.getId());

        CartItem item;

        if (optionalItem.isPresent()) {

            item = optionalItem.get();

            int newQuantity =
                    item.getQuantity() + request.getQuantity();

            if (newQuantity > product.getStock()) {
                throw new BadRequestException(
                        "Only "
                                + product.getStock()
                                + " items are available in stock");
            }

            item.setQuantity(newQuantity);

            if (item.getPrice() == null) {
                item.setPrice(product.getPrice());
            }

        } else {

            item = new CartItem();

            item.setProduct(product);
            item.setQuantity(request.getQuantity());
            item.setPrice(product.getPrice());

            cart.addItem(item);
        }

        item.setTotalPrice(
                item.getPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        item.getQuantity()))
        );

        cartItemRepository.save(item);

        calculateCart(cart);

        Cart savedCart = cartRepository.save(cart);

        return mapToCartResponse(savedCart);
    }

    // =========================================================
    // GET MY CART
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public CartResponse getMyCart() {

        User user = getLoggedInUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart not found"));

        return mapToCartResponse(cart);
    }

    // =========================================================
    // UPDATE QUANTITY
    // =========================================================

    @Override
    public CartResponse updateQuantity(
            Long productId,
            Integer quantity) {

        if (productId == null) {
            throw new BadRequestException(
                    "Product ID is required");
        }

        if (quantity == null || quantity <= 0) {
            throw new BadRequestException(
                    "Quantity must be greater than zero");
        }

        Cart cart = getCartEntity();

        CartItem item =
                cartItemRepository
                        .findByCartIdAndProductId(
                                cart.getId(),
                                productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Item not found in cart"));

        Product product = item.getProduct();

        if (product == null) {
            throw new ResourceNotFoundException(
                    "Product not found");
        }

        if (!Boolean.TRUE.equals(product.getActive())) {
            throw new BadRequestException(
                    "Product is not active");
        }

        if (product.getStock() == null
                || product.getStock() <= 0) {

            throw new BadRequestException(
                    "Product is out of stock");
        }

        if (quantity > product.getStock()) {
            throw new BadRequestException(
                    "Only "
                            + product.getStock()
                            + " items are available in stock");
        }

        item.setQuantity(quantity);

        item.setTotalPrice(
                item.getPrice()
                        .multiply(
                                BigDecimal.valueOf(quantity))
        );

        cartItemRepository.save(item);

        calculateCart(cart);

        Cart savedCart = cartRepository.save(cart);

        return mapToCartResponse(savedCart);
    }

    // =========================================================
    // REMOVE ITEM
    // =========================================================

    @Override
    public void removeItem(Long productId) {

        if (productId == null) {
            throw new BadRequestException(
                    "Product ID is required");
        }

        Cart cart = getCartEntity();

        CartItem item =
                cartItemRepository
                        .findByCartIdAndProductId(
                                cart.getId(),
                                productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Item not found in cart"));

        cart.removeItem(item);

        cartItemRepository.delete(item);

        calculateCart(cart);

        cartRepository.save(cart);
    }

    // =========================================================
    // CLEAR CART
    // =========================================================

    @Override
    public void clearCart() {

        Cart cart = getCartEntity();

        cart.getCartItems().clear();

        cart.setTotalAmount(BigDecimal.ZERO);
        cart.setTotalItems(0);

        cartRepository.save(cart);
    }

    // =========================================================
    // CREATE CART
    // =========================================================

    private Cart createCart(User user) {

        Cart cart = new Cart();

        cart.setUser(user);
        cart.setActive(true);
        cart.setTotalAmount(BigDecimal.ZERO);
        cart.setTotalItems(0);

        return cartRepository.save(cart);
    }

    // =========================================================
    // GET CART ENTITY
    // =========================================================

    private Cart getCartEntity() {

        User user = getLoggedInUser();

        return cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart not found"));
    }

    // =========================================================
    // GET LOGGED-IN USER
    // =========================================================

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()) {

            throw new BadRequestException(
                    "User is not authenticated");
        }

        String email = authentication.getName();

        if (email == null || email.isBlank()) {

            throw new BadRequestException(
                    "Authenticated user email not found");
        }

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: "
                                        + email));
    }

    // =========================================================
    // CALCULATE CART TOTALS
    // =========================================================

    private void calculateCart(Cart cart) {

        BigDecimal totalAmount = BigDecimal.ZERO;

        int totalItems = 0;

        for (CartItem item : cart.getCartItems()) {

            if (item == null) {
                continue;
            }

            if (item.getQuantity() == null
                    || item.getQuantity() <= 0) {

                continue;
            }

            if (item.getTotalPrice() == null) {
                continue;
            }

            totalAmount =
                    totalAmount.add(
                            item.getTotalPrice());

            totalItems += item.getQuantity();
        }

        cart.setTotalAmount(totalAmount);
        cart.setTotalItems(totalItems);
    }

    // =========================================================
    // MAP ENTITY -> RESPONSE DTO
    // =========================================================

    private CartResponse mapToCartResponse(Cart cart) {

        List<CartItemResponse> items =
                cart.getCartItems()
                        .stream()
                        .map(this::mapToCartItemResponse)
                        .toList();

        return new CartResponse(
                cart.getId(),
                cart.getTotalAmount(),
                cart.getTotalItems(),
                cart.getActive(),
                cart.getCreatedAt(),
                cart.getUpdatedAt(),
                items
        );
    }

    // =========================================================
    // MAP CART ITEM -> RESPONSE DTO
    // =========================================================

    private CartItemResponse mapToCartItemResponse(
            CartItem item) {

        Product product = item.getProduct();

        return new CartItemResponse(
                item.getId(),
                product != null
                        ? product.getId()
                        : null,
                product != null
                        ? product.getName()
                        : null,
                item.getQuantity(),
                item.getPrice(),
                item.getTotalPrice()
        );
    }
}