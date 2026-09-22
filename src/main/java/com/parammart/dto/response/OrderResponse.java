package com.parammart.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;

    private Long addressId;

    private OrderAddressResponse address;

    private BigDecimal totalAmount;

    private Integer totalItems;

    private String status;

    private LocalDateTime orderedAt;

    private List<OrderItemResponse> orderItems;
}