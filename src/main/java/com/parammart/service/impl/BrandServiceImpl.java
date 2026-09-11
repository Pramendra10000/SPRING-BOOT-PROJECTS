package com.parammart.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parammart.dto.request.BrandRequest;
import com.parammart.dto.response.BrandResponse;
import com.parammart.entity.Brand;
import com.parammart.entity.Category;
import com.parammart.exception.ResourceAlreadyExistsException;
import com.parammart.exception.ResourceNotFoundException;
import com.parammart.repository.BrandRepository;
import com.parammart.repository.CategoryRepository;
import com.parammart.service.BrandService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    // =========================================================
    // CREATE BRAND
    // =========================================================

    @Override
    @Transactional
    public BrandResponse createBrand(BrandRequest request) {

        if (brandRepository.existsByNameIgnoreCase(request.name())) {
            throw new ResourceAlreadyExistsException(
                    "Brand already exists."
            );
        }

        Category category = categoryRepository.findById(
                request.categoryId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Category not found with id: "
                                + request.categoryId()
                )
        );

        Brand brand = Brand.builder()
                .name(request.name())
                .description(request.description())
                .active(
                    request.active() == null
                        ? true
                        : request.active()
                )
                .category(category)
                .build();

        Brand savedBrand = brandRepository.save(brand);

        return mapToResponse(savedBrand);
    }

    // =========================================================
    // UPDATE BRAND
    // =========================================================

    @Override
    @Transactional
    public BrandResponse updateBrand(
            Long id,
            BrandRequest request) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Brand not found with id: " + id
                        )
                );

        Category category = categoryRepository.findById(
                request.categoryId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Category not found with id: "
                                + request.categoryId()
                )
        );

        brand.setName(request.name());
        brand.setDescription(request.description());
        brand.setActive(
                request.active() == null
                        ? true
                        : request.active()
        );
        brand.setCategory(category);

        Brand updatedBrand = brandRepository.save(brand);

        return mapToResponse(updatedBrand);
    }

    // =========================================================
    // GET BRAND BY ID
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public BrandResponse getBrand(Long id) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Brand not found with id: " + id
                        )
                );

        return mapToResponse(brand);
    }

    // =========================================================
    // GET ALL BRANDS
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<BrandResponse> getAllBrands(
            Pageable pageable) {

        return brandRepository
                .findAll(pageable)
                .map(this::mapToResponse);
    }

    // =========================================================
    // DELETE BRAND
    // =========================================================

    @Override
    @Transactional
    public void deleteBrand(Long id) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Brand not found with id: " + id
                        )
                );

        brandRepository.delete(brand);
    }

    // =========================================================
    // ENTITY → RESPONSE
    // =========================================================

    private BrandResponse mapToResponse(Brand brand) {

        return new BrandResponse(
                brand.getId(),
                brand.getName(),
                brand.getDescription(),
                brand.getActive(),

                brand.getCategory().getId(),
                brand.getCategory().getName(),

                brand.getCreatedAt(),
                brand.getUpdatedAt()
        );
    }
}