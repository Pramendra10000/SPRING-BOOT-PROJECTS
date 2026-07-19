package com.parammart.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BrandResponse createBrand(@Valid @RequestBody BrandRequest request) {
        return brandService.createBrand(request);
    }

    @GetMapping("/{id}")
    public BrandResponse getBrand(@PathVariable Long id) {
        return brandService.getBrand(id);
    }

    @GetMapping
    public Page<BrandResponse> getAllBrands(Pageable pageable) {
        return brandService.getAllBrands(pageable);
    }

    @PutMapping("/{id}")
    public BrandResponse updateBrand(
            @PathVariable Long id,
            @Valid @RequestBody BrandRequest request) {

        return brandService.updateBrand(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
    }
}