package com.parammart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parammart.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByOrderId(Long orderId);

}