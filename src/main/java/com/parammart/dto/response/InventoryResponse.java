package com.parammart.dto.response;

import java.time.LocalDateTime;

public class InventoryResponse {

    private Long id;

    private Long productId;

    private String productName;

    private Integer availableStock;

    private Integer reservedStock;

    private Integer minimumStock;

    private Integer maximumStock;

    private String warehouseLocation;

    private Boolean active;

    private LocalDateTime updatedAt;

    public InventoryResponse() {
    }

    // Generate Getters & Setters using your IDE
}