package com.parammart.service;

import java.util.List;

import com.parammart.dto.request.PlaceOrderRequest;
import com.parammart.dto.response.OrderResponse;

public interface OrderService {

    OrderResponse placeOrder(PlaceOrderRequest request);

    List<OrderResponse> getMyOrders();

    OrderResponse getOrderById(Long id);

    OrderResponse cancelOrder(Long id);
}