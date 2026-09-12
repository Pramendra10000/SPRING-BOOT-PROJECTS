package com.parammart.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.parammart.dto.response.ProductMediaResponse;
import com.parammart.enums.MediaType;

public interface ProductMediaService {

    List<ProductMediaResponse> getProductMedia(Long productId);

    ProductMediaResponse getPrimaryMedia(Long productId);

    ProductMediaResponse uploadMedia(
            Long productId,
            MultipartFile file,
            MediaType mediaType,
            boolean primary,
            String altText
    );

    void setPrimaryMedia(Long productId, Long mediaId);

    void deleteMedia(Long productId, Long mediaId);
}