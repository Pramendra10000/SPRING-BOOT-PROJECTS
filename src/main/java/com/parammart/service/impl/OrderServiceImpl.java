package com.parammart.service.impl;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parammart.entity.*;
import com.parammart.exception.BadRequestException;
import com.parammart.exception.ResourceNotFoundException;
import com.parammart.repository.*;
import com.parammart.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            CartRepository cartRepository,
            InventoryRepository inventoryRepository,
            UserRepository userRepository) {

        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.inventoryRepository = inventoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Order placeOrder() {

        User user = getLoggedInUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new BadRequestException("Cart is empty.");
        }

        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(cart.getTotalAmount());
        order.setTotalItems(cart.getTotalItems());
        order.setStatus(OrderStatus.PENDING);

        for (CartItem cartItem : cart.getCartItems()) {

            Inventory inventory = inventoryRepository
                    .findByProductId(cartItem.getProduct().getId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Inventory not found"));

            if (inventory.getAvailableStock() < cartItem.getQuantity()) {
                throw new BadRequestException(
                        cartItem.getProduct().getName() + " stock not available.");
            }

            inventory.setAvailableStock(
                    inventory.getAvailableStock() - cartItem.getQuantity());

            inventoryRepository.save(inventory);

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
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

        return savedOrder;
    }

    @Override
    public List<Order> getMyOrders() {

        User user = getLoggedInUser();

        return orderRepository.findByUserId(user.getId());
    }

    @Override
    public Order getOrderById(Long id) {

        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));
    }

    @Override
    public Order cancelOrder(Long id) {

        Order order = getOrderById(id);

        order.setStatus(OrderStatus.CANCELLED);

        return orderRepository.save(order);
    }

    // =============================

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

}