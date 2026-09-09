package com.parammart.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.parammart.dto.request.ProductRequest;
import com.parammart.dto.response.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(
            Long id,
            ProductRequest request);

    ProductResponse getProduct(Long id);

    Page<ProductResponse> getAllProducts(
            Pageable pageable);

    // =========================================================
    // SEARCH
    // =========================================================

    Page<ProductResponse> searchProducts(
            String keyword,
            Pageable pageable);

    // =========================================================
    // CATEGORY FILTER
    // =========================================================

    Page<ProductResponse> getProductsByCategory(
            Long categoryId,
            Pageable pageable);

    Page<ProductResponse> searchProductsByCategory(
            String keyword,
            Long categoryId,
            Pageable pageable);

    // =========================================================
    // BRAND FILTER
    // =========================================================

    Page<ProductResponse> getProductsByBrand(
            Long brandId,
            Pageable pageable);

    Page<ProductResponse> searchProductsByBrand(
            String keyword,
            Long brandId,
            Pageable pageable);

    // =========================================================
    // CATEGORY + BRAND FILTER
    // =========================================================

    Page<ProductResponse> getProductsByCategoryAndBrand(
            Long categoryId,
            Long brandId,
            Pageable pageable);

    Page<ProductResponse> searchProductsByCategoryAndBrand(
            String keyword,
            Long categoryId,
            Long brandId,
            Pageable pageable);

    // =========================================================
    // DELETE
    // =========================================================

    void deleteProduct(Long id);
}