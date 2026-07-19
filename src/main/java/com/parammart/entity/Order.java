package com.parammart.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    private BigDecimal totalAmount;

    private Integer totalItems;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderedAt;

    @PrePersist
    public void onCreate() {
        orderedAt = LocalDateTime.now();

        if(status == null) {
            status = OrderStatus.PENDING;
        }
    }

}