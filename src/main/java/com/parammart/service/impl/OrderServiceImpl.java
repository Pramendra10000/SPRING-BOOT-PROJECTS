package com.parammart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parammart.dto.request.PlaceOrderRequest;
import com.parammart.dto.response.OrderAddressResponse;
import com.parammart.dto.response.OrderItemResponse;
import com.parammart.dto.response.OrderResponse;
import com.parammart.entity.Address;
import com.parammart.entity.Cart;
import com.parammart.entity.CartItem;
import com.parammart.entity.Inventory;
import com.parammart.entity.Order;
import com.parammart.entity.OrderItem;
import com.parammart.entity.OrderStatus;
import com.parammart.entity.Product;
import com.parammart.entity.User;
import com.parammart.exception.BadRequestException;
import com.parammart.exception.ResourceNotFoundException;
import com.parammart.repository.AddressRepository;
import com.parammart.repository.CartRepository;
import com.parammart.repository.InventoryRepository;
import com.parammart.repository.OrderRepository;
import com.parammart.repository.UserRepository;
import com.parammart.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            CartRepository cartRepository,
            InventoryRepository inventoryRepository,
            UserRepository userRepository,
            AddressRepository addressRepository) {

        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.inventoryRepository = inventoryRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public OrderResponse placeOrder(PlaceOrderRequest request) {

        if (request == null || request.getAddressId() == null) {
            throw new BadRequestException("Delivery address is required.");
        }

        User user = getLoggedInUser();

        Address address = addressRepository
                .findByIdAndUserId(request.getAddressId(), user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Delivery address not found."));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        if (cart.getCartItems() == null ||
                cart.getCartItems().isEmpty()) {

            throw new BadRequestException("Cart is empty.");
        }

        Order order = new Order();

        order.setUser(user);
        order.setAddress(address);
        order.setTotalAmount(cart.getTotalAmount());
        order.setTotalItems(cart.getTotalItems());
        order.setStatus(OrderStatus.PENDING);

        for (CartItem cartItem : cart.getCartItems()) {

            if (cartItem.getProduct() == null) {
                throw new ResourceNotFoundException(
                        "Product not found in cart.");
            }

            Product product = cartItem.getProduct();

            Inventory inventory = inventoryRepository
                    .findByProductId(product.getId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Inventory not found for product: "
                                            + product.getName()));

            if (inventory.getAvailableStock() == null ||
                    inventory.getAvailableStock()
                            < cartItem.getQuantity()) {

                throw new BadRequestException(
                        product.getName()
                                + " stock not available.");
            }

            inventory.setAvailableStock(
                    inventory.getAvailableStock()
                            - cartItem.getQuantity());

            inventoryRepository.save(inventory);

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            order.getOrderItems().add(orderItem);
        }

        Order savedOrder = orderRepository.save(order);

        cart.getCartItems().clear();
        cart.setTotalAmount(java.math.BigDecimal.ZERO);
        cart.setTotalItems(0);

        cartRepository.save(cart);

        return mapToOrderResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getMyOrders() {

        User user = getLoggedInUser();

        return orderRepository
                .findByUserId(user.getId())
                .stream()
                .map(this::mapToOrderResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {

        User user = getLoggedInUser();

        Order order = orderRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found"));

        return mapToOrderResponse(order);
    }

    @Override
    public OrderResponse cancelOrder(Long id) {

        User user = getLoggedInUser();

        Order order = orderRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        OrderStatus currentStatus = order.getStatus();

        // Already cancelled
        if (currentStatus == OrderStatus.CANCELLED) {
            throw new BadRequestException("Order is already cancelled.");
        }

        // Orders that can no longer be cancelled
        if (currentStatus == OrderStatus.SHIPPED ||
                currentStatus == OrderStatus.DELIVERED) {

            throw new BadRequestException(
                    "Order cannot be cancelled after it has been shipped.");
        }

        // Restore inventory
        if (order.getOrderItems() != null) {

            for (OrderItem orderItem : order.getOrderItems()) {

                if (orderItem.getProduct() == null) {
                    continue;
                }

                Long productId = orderItem.getProduct().getId();

                Inventory inventory = inventoryRepository
                        .findByProductId(productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Inventory not found for product: "
                                                + orderItem.getProduct().getName()));

                int restoredQuantity =
                        orderItem.getQuantity() != null
                                ? orderItem.getQuantity()
                                : 0;

                inventory.setAvailableStock(
                        inventory.getAvailableStock() + restoredQuantity);

                inventoryRepository.save(inventory);
            }
        }

        // Cancel order
        order.setStatus(OrderStatus.CANCELLED);

        Order savedOrder = orderRepository.save(order);

        return mapToOrderResponse(savedOrder);
    }

    private OrderResponse mapToOrderResponse(Order order) {

        List<OrderItemResponse> items = new ArrayList<>();

        if (order.getOrderItems() != null) {

            for (OrderItem item : order.getOrderItems()) {

                Product product = item.getProduct();

                items.add(
                        new OrderItemResponse(
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
                        )
                );
            }
        }

        OrderAddressResponse addressResponse = null;

        if (order.getAddress() != null) {
            Address address = order.getAddress();

            addressResponse = new OrderAddressResponse(
                    address.getId(),
                    address.getFullName(),
                    address.getMobile(),
                    address.getAddressLine1(),
                    address.getAddressLine2(),
                    address.getCity(),
                    address.getState(),
                    address.getCountry(),
                    address.getPincode()
            );
        }

        return new OrderResponse(
                order.getId(),
                order.getAddress() != null
                        ? order.getAddress().getId()
                        : null,
                addressResponse,
                order.getTotalAmount(),
                order.getTotalItems(),
                order.getStatus() != null
                        ? order.getStatus().name()
                        : null,
                order.getOrderedAt(),
                items
        );
    }

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

        String email = authentication.getName();

        if (email == null || email.isBlank()) {

            throw new BadRequestException(
                    "Authenticated user email not found.");
        }

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));
    }
}