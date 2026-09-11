package com.parammart.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.parammart.dto.request.BrandRequest;
import com.parammart.dto.response.BrandResponse;
import com.parammart.service.BrandService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    // =========================================================
    // CREATE BRAND
    // =========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BrandResponse createBrand(
            @Valid @RequestBody BrandRequest request) {

        return brandService.createBrand(request);
    }

    // =========================================================
    // GET BRAND BY ID
    // =========================================================

    @GetMapping("/{id}")
    public BrandResponse getBrand(
            @PathVariable Long id) {

        return brandService.getBrand(id);
    }

    // =========================================================
    // GET ALL BRANDS
    // =========================================================
    //
    // Public catalog API.
    //
    // Example:
    //
    // GET /api/brands?page=0&size=100&sort=name,asc
    //
    // =========================================================

    @GetMapping
    public Page<BrandResponse> getAllBrands(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "100")
            int size,

            @RequestParam(defaultValue = "name,asc")
            String sort) {

        Pageable pageable = createPageable(
                page,
                size,
                sort
        );

        return brandService.getAllBrands(pageable);
    }

    // =========================================================
    // UPDATE BRAND
    // =========================================================

    @PutMapping("/{id}")
    public BrandResponse updateBrand(
            @PathVariable Long id,
            @Valid @RequestBody BrandRequest request) {

        return brandService.updateBrand(id, request);
    }

    // =========================================================
    // DELETE BRAND
    // =========================================================

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBrand(
            @PathVariable Long id) {

        brandService.deleteBrand(id);
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