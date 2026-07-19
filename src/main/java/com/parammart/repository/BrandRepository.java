package com.parammart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.parammart.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    // Check if brand already exists
    boolean existsByNameIgnoreCase(String name);

    // Find brand by name
    Optional<Brand> findByNameIgnoreCase(String name);

    // Get all brands of a category
    List<Brand> findByCategoryId(Long categoryId);

    // Get only active brands
    List<Brand> findByActiveTrue();

    // Search by keyword
    List<Brand> findByNameContainingIgnoreCase(String keyword);

    // Pagination
    Page<Brand> findAll(Pageable pageable);

}