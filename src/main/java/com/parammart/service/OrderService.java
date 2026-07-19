package com.parammart.service;

import java.util.List;

import com.parammart.entity.Order;

public interface OrderService {

    Order placeOrder();

    List<Order> getMyOrders();

    Order getOrderById(Long id);

    Order cancelOrder(Long id);

}