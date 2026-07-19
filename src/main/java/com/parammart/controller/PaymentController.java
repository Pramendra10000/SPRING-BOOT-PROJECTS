package com.parammart.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.parammart.dto.request.PaymentRequest;
import com.parammart.dto.response.ApiResponse;
import com.parammart.entity.Payment;
import com.parammart.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<Payment>> makePayment(
            @Valid @RequestBody PaymentRequest request) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Payment Successful",
                        paymentService.makePayment(request)
                ));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public ResponseEntity<ApiResponse<Payment>> getPayment(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Payment Details",
                        paymentService.getPayment(id)
                ));
    }

    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public ResponseEntity<ApiResponse<List<Payment>>> getOrderPayments(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Payments Retrieved",
                        paymentService.getPaymentsByOrder(orderId)
                ));
    }

}