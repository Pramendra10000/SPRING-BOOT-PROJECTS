package com.parammart.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;

public record ProductRequest(

        @NotBlank
        String name,

        @NotBlank
        String sku,

        String description,

        @DecimalMin("0.01")
        BigDecimal price,

        @Min(0)
        Integer stock,

        Boolean active,

        @NotNull
        Long categoryId,

        @NotNull
        Long brandId

) {}