package com.parammart.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parammart.dto.request.ProductRequest;
import com.parammart.dto.response.ProductResponse;
import com.parammart.entity.Brand;
import com.parammart.entity.Category;
import com.parammart.entity.Product;
import com.parammart.exception.ResourceAlreadyExistsException;
import com.parammart.exception.ResourceNotFoundException;
import com.parammart.repository.BrandRepository;
import com.parammart.repository.CategoryRepository;
import com.parammart.repository.ProductRepository;
import com.parammart.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    // =========================================================
    // CREATE PRODUCT
    // =========================================================

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {

        log.info("Creating product with SKU: {}", request.sku());

        // Check duplicate SKU
        if (productRepository.existsBySkuIgnoreCase(request.sku())) {

            log.warn("Duplicate SKU found: {}", request.sku());

            throw new ResourceAlreadyExistsException(
                    "SKU already exists."
            );
        }

        // Find category
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + request.categoryId()
                ));

        // Find brand
        Brand brand = brandRepository.findById(request.brandId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Brand not found with id: " + request.brandId()
                ));

        // Build product
        Product product = Product.builder()
                .name(request.name())
                .sku(request.sku())
                .description(request.description())
                .price(request.price())
                .stock(request.stock())
                .active(request.active() == null ? true : request.active())
                .category(category)
                .brand(brand)
                .build();

        // Save product
        Product savedProduct = productRepository.save(product);

        log.info(
                "Product created successfully. Product ID: {}",
                savedProduct.getId()
        );

        return map(savedProduct);
    }

    // =========================================================
    // UPDATE PRODUCT
    // =========================================================

    @Override
    @Transactional
    public ProductResponse updateProduct(
            Long id,
            ProductRequest request
    ) {

        log.info("Updating product with ID: {}", id);

        // Find existing product
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + id
                ));

        // Check duplicate SKU
        // Ignore the current product itself
        if (productRepository.existsBySkuIgnoreCaseAndIdNot(
                request.sku(),
                id
        )) {

            log.warn(
                    "Duplicate SKU found while updating product. SKU: {}",
                    request.sku()
            );

            throw new ResourceAlreadyExistsException(
                    "SKU already exists."
            );
        }

        // Find category
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + request.categoryId()
                ));

        // Find brand
        Brand brand = brandRepository.findById(request.brandId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Brand not found with id: " + request.brandId()
                ));

        // Update product fields
        product.setName(request.name());
        product.setSku(request.sku());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());

        // Keep active handling consistent with create
        product.setActive(
                request.active() == null
                        ? true
                        : request.active()
        );

        product.setCategory(category);
        product.setBrand(brand);

        // Save updated product
        Product updatedProduct = productRepository.save(product);

        log.info(
                "Product updated successfully. Product ID: {}",
                updatedProduct.getId()
        );

        return map(updatedProduct);
    }

    // =========================================================
    // GET PRODUCT BY ID
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {

        log.debug("Fetching product with ID: {}", id);

        return productRepository.findById(id)
                .map(this::map)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + id
                ));
    }

    // =========================================================
    // GET ALL PRODUCTS
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(
            Pageable pageable
    ) {

        log.debug(
                "Fetching all products. Page: {}, Size: {}",
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

        return productRepository.findAll(pageable)
                .map(this::map);
    }

    // =========================================================
    // SEARCH PRODUCTS
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> searchProducts(
            String keyword,
            Pageable pageable
    ) {

        log.debug(
                "Searching products with keyword: {}",
                keyword
        );

        return productRepository
                .findByNameContainingIgnoreCase(keyword, pageable)
                .map(this::map);
    }

    // =========================================================
    // DELETE PRODUCT
    // =========================================================

    @Override
    @Transactional
    public void deleteProduct(Long id) {

        log.info("Deleting product with ID: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + id
                ));

        productRepository.delete(product);

        log.info(
                "Product deleted successfully. Product ID: {}",
                id
        );
    }

    // =========================================================
    // ENTITY -> RESPONSE MAPPER
    // =========================================================

    private ProductResponse map(Product product) {

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getSku(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getActive(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getBrand().getId(),
                product.getBrand().getName(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}