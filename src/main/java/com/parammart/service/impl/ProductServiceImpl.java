package com.parammart.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parammart.dto.request.ProductRequest;
import com.parammart.dto.response.ProductResponse;
import com.parammart.entity.Brand;
import com.parammart.entity.Category;
import com.parammart.entity.Product;
import com.parammart.entity.ProductMedia;
import com.parammart.exception.ResourceAlreadyExistsException;
import com.parammart.exception.ResourceNotFoundException;
import com.parammart.repository.BrandRepository;
import com.parammart.repository.CategoryRepository;
import com.parammart.repository.ProductMediaRepository;
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
    private final ProductMediaRepository productMediaRepository;

    // =========================================================
    // CREATE PRODUCT
    // =========================================================

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {

        log.info("Creating product with SKU: {}", request.sku());

        if (productRepository.existsBySkuIgnoreCase(request.sku())) {

            log.warn("Duplicate SKU found: {}", request.sku());

            throw new ResourceAlreadyExistsException(
                    "SKU already exists."
            );
        }

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + request.categoryId()
                ));

        Brand brand = brandRepository.findById(request.brandId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Brand not found with id: " + request.brandId()
                ));

        Product product = Product.builder()
                .name(request.name())
                .sku(request.sku())
                .description(request.description())
                .price(request.price())
                .stock(request.stock())
                .active(
                        request.active() == null
                                ? true
                                : request.active()
                )
                .category(category)
                .brand(brand)
                .build();

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

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + id
                ));

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

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + request.categoryId()
                ));

        Brand brand = brandRepository.findById(request.brandId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Brand not found with id: " + request.brandId()
                ));

        product.setName(request.name());
        product.setSku(request.sku());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setActive(
                request.active() == null
                        ? true
                        : request.active()
        );
        product.setCategory(category);
        product.setBrand(brand);

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

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + id
                ));

        return map(product);
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

        Page<Product> productPage =
                productRepository.findAll(pageable);

        return mapPage(productPage);
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

        Page<Product> productPage =
                productRepository.findByNameContainingIgnoreCase(
                        keyword,
                        pageable
                );

        return mapPage(productPage);
    }

    // =========================================================
    // CATEGORY FILTER
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getProductsByCategory(
            Long categoryId,
            Pageable pageable
    ) {

        log.debug(
                "Fetching products for category ID: {}",
                categoryId
        );

        Page<Product> productPage =
                productRepository.findByCategoryId(
                        categoryId,
                        pageable
                );

        return mapPage(productPage);
    }

    // =========================================================
    // SEARCH + CATEGORY
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> searchProductsByCategory(
            String keyword,
            Long categoryId,
            Pageable pageable
    ) {

        log.debug(
                "Searching products with keyword: {} in category ID: {}",
                keyword,
                categoryId
        );

        Page<Product> productPage =
                productRepository
                        .findByNameContainingIgnoreCaseAndCategoryId(
                                keyword,
                                categoryId,
                                pageable
                        );

        return mapPage(productPage);
    }

    // =========================================================
    // BRAND FILTER
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getProductsByBrand(
            Long brandId,
            Pageable pageable
    ) {

        log.debug(
                "Fetching products for brand ID: {}",
                brandId
        );

        Page<Product> productPage =
                productRepository.findByBrandId(
                        brandId,
                        pageable
                );

        return mapPage(productPage);
    }

    // =========================================================
    // SEARCH + BRAND
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> searchProductsByBrand(
            String keyword,
            Long brandId,
            Pageable pageable
    ) {

        log.debug(
                "Searching products with keyword: {} in brand ID: {}",
                keyword,
                brandId
        );

        Page<Product> productPage =
                productRepository
                        .findByNameContainingIgnoreCaseAndBrandId(
                                keyword,
                                brandId,
                                pageable
                        );

        return mapPage(productPage);
    }

    // =========================================================
    // CATEGORY + BRAND FILTER
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getProductsByCategoryAndBrand(
            Long categoryId,
            Long brandId,
            Pageable pageable
    ) {

        log.debug(
                "Fetching products for category ID: {} and brand ID: {}",
                categoryId,
                brandId
        );

        Page<Product> productPage =
                productRepository.findByCategoryIdAndBrandId(
                        categoryId,
                        brandId,
                        pageable
                );

        return mapPage(productPage);
    }

    // =========================================================
    // SEARCH + CATEGORY + BRAND
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> searchProductsByCategoryAndBrand(
            String keyword,
            Long categoryId,
            Long brandId,
            Pageable pageable
    ) {

        log.debug(
                "Searching products with keyword: {} in category ID: {} and brand ID: {}",
                keyword,
                categoryId,
                brandId
        );

        Page<Product> productPage =
                productRepository
                        .findByNameContainingIgnoreCaseAndCategoryIdAndBrandId(
                                keyword,
                                categoryId,
                                brandId,
                                pageable
                        );

        return mapPage(productPage);
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
    // PAGE -> RESPONSE MAPPER
    // =========================================================

    private Page<ProductResponse> mapPage(
            Page<Product> productPage
    ) {

        List<Product> products = productPage.getContent();

        /*
         * No products on this page.
         * No media query is required.
         */
        if (products.isEmpty()) {

            return productPage.map(product ->
                    buildResponse(product, null)
            );
        }

        /*
         * Collect all product IDs from the current page.
         */
        List<Long> productIds = products.stream()
                .map(Product::getId)
                .toList();

        /*
         * Fetch all primary images for the current page
         * using ONE database query.
         */
        Map<Long, String> primaryImageMap =
                productMediaRepository
                        .findByProductIdInAndIsPrimaryTrueAndActiveTrue(
                                productIds
                        )
                        .stream()
                        .collect(Collectors.toMap(
                                media -> media.getProduct().getId(),
                                ProductMedia::getMediaUrl,
                                (existing, replacement) -> existing
                        ));

        /*
         * Build ProductResponse objects using the
         * already-fetched primary image map.
         */
        return productPage.map(product ->
                buildResponse(
                        product,
                        primaryImageMap.get(product.getId())
                )
        );
    }

    // =========================================================
    // SINGLE PRODUCT MAPPER
    // =========================================================

    private ProductResponse map(Product product) {

        String primaryImageUrl =
                productMediaRepository
                        .findByProductIdAndIsPrimaryTrueAndActiveTrue(
                                product.getId()
                        )
                        .map(ProductMedia::getMediaUrl)
                        .orElse(null);

        return buildResponse(
                product,
                primaryImageUrl
        );
    }

    // =========================================================
    // ENTITY -> RESPONSE
    // =========================================================

    private ProductResponse buildResponse(
            Product product,
            String primaryImageUrl
    ) {

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

                primaryImageUrl,

                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}