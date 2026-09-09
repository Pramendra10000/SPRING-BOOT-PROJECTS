package com.parammart.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.parammart.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsBySkuIgnoreCase(String sku);

    boolean existsBySkuIgnoreCaseAndIdNot(String sku, Long id);

    Optional<Product> findBySkuIgnoreCase(String sku);

    // =========================================================
    // CATEGORY FILTER
    // =========================================================

    Page<Product> findByCategoryId(
            Long categoryId,
            Pageable pageable);

    // =========================================================
    // BRAND FILTER
    // =========================================================

    Page<Product> findByBrandId(
            Long brandId,
            Pageable pageable);

    // =========================================================
    // CATEGORY + BRAND FILTER
    // =========================================================

    Page<Product> findByCategoryIdAndBrandId(
            Long categoryId,
            Long brandId,
            Pageable pageable);

    // =========================================================
    // SEARCH
    // =========================================================

    Page<Product> findByNameContainingIgnoreCase(
            String keyword,
            Pageable pageable);

    // =========================================================
    // SEARCH + CATEGORY
    // =========================================================

    Page<Product> findByNameContainingIgnoreCaseAndCategoryId(
            String keyword,
            Long categoryId,
            Pageable pageable);

    // =========================================================
    // SEARCH + BRAND
    // =========================================================

    Page<Product> findByNameContainingIgnoreCaseAndBrandId(
            String keyword,
            Long brandId,
            Pageable pageable);

    // =========================================================
    // SEARCH + CATEGORY + BRAND
    // =========================================================

    Page<Product> findByNameContainingIgnoreCaseAndCategoryIdAndBrandId(
            String keyword,
            Long categoryId,
            Long brandId,
            Pageable pageable);

    // =========================================================
    // ACTIVE PRODUCTS
    // =========================================================

    Page<Product> findByActiveTrue(Pageable pageable);
}