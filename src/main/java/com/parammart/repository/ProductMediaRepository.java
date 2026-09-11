package com.parammart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parammart.entity.ProductMedia;
import com.parammart.enums.MediaType;

public interface ProductMediaRepository extends JpaRepository<ProductMedia, Long> {

    List<ProductMedia> findByProductIdAndActiveTrueOrderByDisplayOrderAsc(Long productId);

    List<ProductMedia> findByProductIdAndMediaTypeAndActiveTrueOrderByDisplayOrderAsc(
            Long productId,
            MediaType mediaType
    );

    Optional<ProductMedia> findByProductIdAndIsPrimaryTrueAndActiveTrue(Long productId);

    Optional<ProductMedia> findByIdAndProductId(Long id, Long productId);

    boolean existsByProductIdAndIsPrimaryTrueAndActiveTrue(Long productId);

    long countByProductIdAndActiveTrue(Long productId);

    long countByProductIdAndMediaTypeAndActiveTrue(
            Long productId,
            MediaType mediaType
    );

    void deleteByProductId(Long productId);
}