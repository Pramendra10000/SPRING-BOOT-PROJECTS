package com.parammart.dto.response;

import java.time.LocalDateTime;

import com.parammart.enums.MediaType;

public record ProductMediaResponse(

        Long id,

        Long productId,

        MediaType mediaType,

        String fileName,

        String storageKey,

        String mediaUrl,

        String contentType,

        Long fileSize,

        Boolean isPrimary,

        Integer displayOrder,

        String altText,

        Boolean active,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}