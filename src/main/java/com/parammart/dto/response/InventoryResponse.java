package com.parammart.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {

    private Long id;

    private Long productId;

    private String productName;

    private String sku;

    private Integer availableStock;

    private Integer reservedStock;

    private Integer minimumStock;

    private Integer maximumStock;

    private String warehouseLocation;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}