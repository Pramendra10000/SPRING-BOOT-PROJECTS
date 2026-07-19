package com.parammart.service;

import java.util.List;

import com.parammart.dto.request.PaymentRequest;
import com.parammart.entity.Payment;

public interface PaymentService {

    Payment makePayment(PaymentRequest request);

    Payment getPayment(Long id);

    List<Payment> getPaymentsByOrder(Long orderId);

}