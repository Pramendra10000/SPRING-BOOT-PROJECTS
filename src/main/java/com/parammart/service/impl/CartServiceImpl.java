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
import com.parammart.entity.Inventory;
import com.parammart.entity.Product;
import com.parammart.entity.User;
import com.parammart.exception.BadRequestException;
import com.parammart.exception.ResourceNotFoundException;
import com.parammart.repository.CartItemRepository;
import com.parammart.repository.CartRepository;
import com.parammart.repository.InventoryRepository;
import com.parammart.repository.ProductRepository;
import com.parammart.repository.UserRepository;
import com.parammart.service.CartService;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository,
            InventoryRepository inventoryRepository,
            UserRepository userRepository) {

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.userRepository = userRepository;
    }

    // =========================================================
    // ADD TO CART
    // =========================================================

    @Override
    public CartResponse addToCart(CartRequest request) {

        if (request == null) {

            throw new BadRequestException(
                    "Cart request cannot be null.");
        }

        if (request.getProductId() == null) {

            throw new BadRequestException(
                    "Product ID is required.");
        }

        if (request.getQuantity() == null ||
                request.getQuantity() <= 0) {

            throw new BadRequestException(
                    "Quantity must be greater than zero.");
        }

        User user = getLoggedInUser();

        Cart cart = cartRepository
                .findByUserId(user.getId())
                .orElseGet(() -> createCart(user));

        // -----------------------------------------------------
        // PRODUCT
        // -----------------------------------------------------

        Product product = productRepository
                .findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: "
                                        + request.getProductId()));

        if (!Boolean.TRUE.equals(product.getActive())) {

            throw new BadRequestException(
                    "Product is not active.");
        }

        // -----------------------------------------------------
        // INVENTORY
        // Inventory is the source of truth for stock.
        // -----------------------------------------------------

        Inventory inventory =
                getInventoryForProduct(product);

        validateInventoryAvailable(inventory);

        int availableStock =
                inventory.getAvailableStock();

        if (request.getQuantity() > availableStock) {

            throw new BadRequestException(
                    "Only " + availableStock
                            + " items are available in stock.");
        }

        // -----------------------------------------------------
        // EXISTING CART ITEM
        // -----------------------------------------------------

        Optional<CartItem> optionalItem =
                cartItemRepository
                        .findByCartIdAndProductId(
                                cart.getId(),
                                product.getId());

        CartItem item;

        if (optionalItem.isPresent()) {

            item = optionalItem.get();

            int currentQuantity =
                    item.getQuantity() != null
                            ? item.getQuantity()
                            : 0;

            int newQuantity =
                    currentQuantity + request.getQuantity();

            if (newQuantity > availableStock) {

                throw new BadRequestException(
                        "Only " + availableStock
                                + " items are available in stock.");
            }

            item.setQuantity(newQuantity);

            // Keep current cart price if already present.
            // If missing, use current product price.
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

        // -----------------------------------------------------
        // ITEM TOTAL
        // -----------------------------------------------------

        if (item.getPrice() == null) {

            throw new BadRequestException(
                    "Product price is not configured.");
        }

        item.setTotalPrice(
                item.getPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        item.getQuantity())));

        cartItemRepository.save(item);

        // -----------------------------------------------------
        // CART TOTAL
        // -----------------------------------------------------

        calculateCart(cart);

        Cart savedCart =
                cartRepository.save(cart);

        return mapToCartResponse(savedCart);
    }

    // =========================================================
    // GET MY CART
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public CartResponse getMyCart() {

        User user = getLoggedInUser();

        Cart cart = cartRepository
                .findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart not found."));

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
                    "Product ID is required.");
        }

        if (quantity == null || quantity <= 0) {

            throw new BadRequestException(
                    "Quantity must be greater than zero.");
        }

        Cart cart = getCartEntity();

        CartItem item =
                cartItemRepository
                        .findByCartIdAndProductId(
                                cart.getId(),
                                productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Item not found in cart."));

        Product product = item.getProduct();

        if (product == null) {

            throw new ResourceNotFoundException(
                    "Product not found for cart item.");
        }

        if (!Boolean.TRUE.equals(product.getActive())) {

            throw new BadRequestException(
                    "Product is not active.");
        }

        // -----------------------------------------------------
        // INVENTORY CHECK
        // -----------------------------------------------------

        Inventory inventory =
                getInventoryForProduct(product);

        validateInventoryAvailable(inventory);

        int availableStock =
                inventory.getAvailableStock();

        if (quantity > availableStock) {

            throw new BadRequestException(
                    "Only " + availableStock
                            + " items are available in stock.");
        }

        // -----------------------------------------------------
        // UPDATE ITEM
        // -----------------------------------------------------

        if (item.getPrice() == null) {

            throw new BadRequestException(
                    "Cart item price is not configured.");
        }

        item.setQuantity(quantity);

        item.setTotalPrice(
                item.getPrice()
                        .multiply(
                                BigDecimal.valueOf(quantity)));

        cartItemRepository.save(item);

        calculateCart(cart);

        Cart savedCart =
                cartRepository.save(cart);

        return mapToCartResponse(savedCart);
    }

    // =========================================================
    // REMOVE ITEM
    // =========================================================

    @Override
    public void removeItem(Long productId) {

        if (productId == null) {

            throw new BadRequestException(
                    "Product ID is required.");
        }

        Cart cart = getCartEntity();

        CartItem item =
                cartItemRepository
                        .findByCartIdAndProductId(
                                cart.getId(),
                                productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Item not found in cart."));

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

        cart.setTotalAmount(
                BigDecimal.ZERO);

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

        return cartRepository
                .findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart not found."));
    }

    // =========================================================
    // GET LOGGED-IN USER
    // =========================================================

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new BadRequestException(
                    "User is not authenticated.");
        }

        String email =
                authentication.getName();

        if (email == null ||
                email.isBlank()) {

            throw new BadRequestException(
                    "Authenticated user email not found.");
        }

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: "
                                        + email));
    }

    // =========================================================
    // GET INVENTORY
    // =========================================================

    private Inventory getInventoryForProduct(
            Product product) {

        return inventoryRepository
                .findByProductId(product.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Inventory not found for product: "
                                        + product.getName()));
    }

    // =========================================================
    // VALIDATE INVENTORY
    // =========================================================

    private void validateInventoryAvailable(
            Inventory inventory) {

        if (!Boolean.TRUE.equals(
                inventory.getActive())) {

            throw new BadRequestException(
                    "Inventory is inactive.");
        }

        if (inventory.getAvailableStock() == null) {

            throw new BadRequestException(
                    "Inventory stock is not configured.");
        }

        if (inventory.getAvailableStock() <= 0) {

            throw new BadRequestException(
                    "Product is out of stock.");
        }
    }

    // =========================================================
    // CALCULATE CART TOTAL
    // =========================================================

    private void calculateCart(Cart cart) {

        BigDecimal totalAmount =
                BigDecimal.ZERO;

        int totalItems = 0;

        for (CartItem item :
                cart.getCartItems()) {

            if (item == null) {
                continue;
            }

            if (item.getQuantity() == null ||
                    item.getQuantity() <= 0) {
                continue;
            }

            if (item.getTotalPrice() == null) {
                continue;
            }

            totalAmount =
                    totalAmount.add(
                            item.getTotalPrice());

            totalItems +=
                    item.getQuantity();
        }

        cart.setTotalAmount(totalAmount);
        cart.setTotalItems(totalItems);
    }

    // =========================================================
    // MAP CART RESPONSE
    // =========================================================

    private CartResponse mapToCartResponse(
            Cart cart) {

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
                items);
    }

    // =========================================================
    // MAP CART ITEM RESPONSE
    // =========================================================

    private CartItemResponse mapToCartItemResponse(
            CartItem item) {

        Product product =
                item.getProduct();

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
                item.getTotalPrice());
    }
}