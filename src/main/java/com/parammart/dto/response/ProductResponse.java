package com.parammart.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(

        Long id,

        String name,

        String sku,

        String description,

        BigDecimal price,

        Integer stock,

        Boolean active,

        Long categoryId,

        String categoryName,

        Long brandId,

        String brandName,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {}