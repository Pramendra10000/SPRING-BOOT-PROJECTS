package com.parammart.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(unique = true)
    private String transactionId;

    private LocalDateTime paymentDate;

    public Payment() {
    }

    @PrePersist
    public void onCreate() {

        paymentDate = LocalDateTime.now();

        if (paymentStatus == null) {
            paymentStatus = PaymentStatus.PENDING;
        }
    }

    // Generate Getters & Setters
}