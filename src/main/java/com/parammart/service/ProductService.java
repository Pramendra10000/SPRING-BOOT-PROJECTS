package com.parammart.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.parammart.dto.request.ProductRequest;
import com.parammart.dto.response.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(Long id, ProductRequest request);

    ProductResponse getProduct(Long id);

    Page<ProductResponse> getAllProducts(Pageable pageable);

    Page<ProductResponse> searchProducts(String keyword, Pageable pageable);

    void deleteProduct(Long id);
}