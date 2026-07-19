package com.parammart.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    @Override
    public BrandResponse createBrand(BrandRequest request) {

        if (brandRepository.existsByNameIgnoreCase(request.name())) {
            throw new ResourceAlreadyExistsException("Brand already exists.");
        }

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Brand brand = Brand.builder()
                .name(request.name())
                .description(request.description())
                .active(request.active() == null ? true : request.active())
                .category(category)
                .build();

        return mapToResponse(brandRepository.save(brand));
    }

    @Override
    public BrandResponse updateBrand(Long id, BrandRequest request) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        brand.setName(request.name());
        brand.setDescription(request.description());
        brand.setActive(request.active());
        brand.setCategory(category);

        return mapToResponse(brandRepository.save(brand));
    }

    @Override
    public BrandResponse getBrand(Long id) {

        return brandRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));
    }

    @Override
    public Page<BrandResponse> getAllBrands(Pageable pageable) {

        return brandRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public void deleteBrand(Long id) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

        brandRepository.delete(brand);
    }

    private BrandResponse mapToResponse(Brand brand) {

        return new BrandResponse(
                brand.getId(),
                brand.getName(),
                brand.getDescription(),
                brand.getActive(),
                brand.getCategory().getId(),
                brand.getCategory().getName(),
                brand.getCreatedAt(),
                brand.getUpdatedAt());
    }

}