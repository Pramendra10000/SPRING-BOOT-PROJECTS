package com.parammart.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.parammart.dto.request.ProductRequest;
import com.parammart.dto.response.ProductResponse;
import com.parammart.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // =========================================================
    // CREATE PRODUCT
    // =========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(
            @Valid @RequestBody ProductRequest request) {

        return productService.createProduct(request);
    }

    // =========================================================
    // GET PRODUCT BY ID
    // =========================================================

    @GetMapping("/{id}")
    public ProductResponse get(
            @PathVariable Long id) {

        return productService.getProduct(id);
    }

    // =========================================================
    // GET PRODUCTS
    // Supports:
    // 1. All products
    // 2. Category
    // 3. Brand
    // 4. Category + Brand
    // =========================================================

    @GetMapping
    public Page<ProductResponse> getAll(

            @RequestParam(required = false)
            Long categoryId,

            @RequestParam(required = false)
            Long brandId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "12")
            int size,

            @RequestParam(defaultValue = "id,desc")
            String sort) {

        Pageable pageable = createPageable(
                page,
                size,
                sort
        );

        // =====================================================
        // CATEGORY + BRAND
        // =====================================================

        if (categoryId != null && brandId != null) {

            return productService.getProductsByCategoryAndBrand(
                    categoryId,
                    brandId,
                    pageable
            );
        }

        // =====================================================
        // CATEGORY ONLY
        // =====================================================

        if (categoryId != null) {

            return productService.getProductsByCategory(
                    categoryId,
                    pageable
            );
        }

        // =====================================================
        // BRAND ONLY
        // =====================================================

        if (brandId != null) {

            return productService.getProductsByBrand(
                    brandId,
                    pageable
            );
        }

        // =====================================================
        // ALL PRODUCTS
        // =====================================================

        return productService.getAllProducts(pageable);
    }

    // =========================================================
    // SEARCH PRODUCTS
    // Supports:
    // 1. Search
    // 2. Search + Category
    // 3. Search + Brand
    // 4. Search + Category + Brand
    // =========================================================

    @GetMapping("/search")
    public Page<ProductResponse> search(

            @RequestParam
            String keyword,

            @RequestParam(required = false)
            Long categoryId,

            @RequestParam(required = false)
            Long brandId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "12")
            int size,

            @RequestParam(defaultValue = "id,desc")
            String sort) {

        Pageable pageable = createPageable(
                page,
                size,
                sort
        );

        // =====================================================
        // SEARCH + CATEGORY + BRAND
        // =====================================================

        if (categoryId != null && brandId != null) {

            return productService.searchProductsByCategoryAndBrand(
                    keyword,
                    categoryId,
                    brandId,
                    pageable
            );
        }

        // =====================================================
        // SEARCH + CATEGORY
        // =====================================================

        if (categoryId != null) {

            return productService.searchProductsByCategory(
                    keyword,
                    categoryId,
                    pageable
            );
        }

        // =====================================================
        // SEARCH + BRAND
        // =====================================================

        if (brandId != null) {

            return productService.searchProductsByBrand(
                    keyword,
                    brandId,
                    pageable
            );
        }

        // =====================================================
        // SEARCH ONLY
        // =====================================================

        return productService.searchProducts(
                keyword,
                pageable
        );
    }

    // =========================================================
    // UPDATE PRODUCT
    // =========================================================

    @PutMapping("/{id}")
    public ProductResponse update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        return productService.updateProduct(
                id,
                request
        );
    }

    // =========================================================
    // DELETE PRODUCT
    // =========================================================

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id) {

        productService.deleteProduct(id);
    }

    // =========================================================
    // CREATE PAGEABLE
    // =========================================================

    private Pageable createPageable(
            int page,
            int size,
            String sort) {

        String[] sortParts = sort.split(",", 2);

        String field = sortParts[0];

        Sort.Direction direction = Sort.Direction.ASC;

        if (sortParts.length > 1
                && "desc".equalsIgnoreCase(sortParts[1])) {

            direction = Sort.Direction.DESC;
        }

        return PageRequest.of(
                page,
                size,
                Sort.by(direction, field)
        );
    }
}