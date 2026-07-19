package com.parammart.dto.response;

import java.time.LocalDateTime;

public record BrandResponse(

        Long id,

        String name,

        String description,

        Boolean active,

        Long categoryId,

        String categoryName,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {}