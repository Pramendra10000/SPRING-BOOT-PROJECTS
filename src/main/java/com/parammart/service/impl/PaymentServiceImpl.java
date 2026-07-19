package com.parammart.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parammart.dto.request.PaymentRequest;
import com.parammart.entity.Order;
import com.parammart.entity.Payment;
import com.parammart.entity.PaymentStatus;
import com.parammart.exception.ResourceNotFoundException;
import com.parammart.repository.OrderRepository;
import com.parammart.repository.PaymentRepository;
import com.parammart.service.PaymentService;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            OrderRepository orderRepository) {

        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Payment makePayment(PaymentRequest request) {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        Payment payment = new Payment();

        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(request.getPaymentMethod());

        // Payment Gateway Integration Later
        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        payment.setTransactionId(
                UUID.randomUUID().toString());

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(Long id) {

        return paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found"));
    }

    @Override
    public List<Payment> getPaymentsByOrder(Long orderId) {

        return paymentRepository.findByOrderId(orderId);
    }

}