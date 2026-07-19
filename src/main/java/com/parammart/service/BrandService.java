package com.parammart.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.parammart.dto.request.BrandRequest;
import com.parammart.dto.response.BrandResponse;

public interface BrandService {

    BrandResponse createBrand(BrandRequest request);

    BrandResponse updateBrand(Long id, BrandRequest request);

    BrandResponse getBrand(Long id);

    Page<BrandResponse> getAllBrands(Pageable pageable);

    void deleteBrand(Long id);

}