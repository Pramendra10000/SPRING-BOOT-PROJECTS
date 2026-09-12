package com.parammart.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.parammart.dto.response.ProductMediaResponse;
import com.parammart.entity.Product;
import com.parammart.entity.ProductMedia;
import com.parammart.enums.MediaType;
import com.parammart.repository.ProductMediaRepository;
import com.parammart.repository.ProductRepository;
import com.parammart.service.ProductMediaService;
import com.parammart.storage.StorageService;
import com.parammart.storage.StoredFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductMediaServiceImpl implements ProductMediaService {

    private static final Set<String> IMAGE_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp",
            "image/avif"
    );

    private static final Set<String> VIDEO_TYPES = Set.of(
            "video/mp4",
            "video/webm"
    );

    private static final Set<String> DOCUMENT_TYPES = Set.of(
            "application/pdf"
    );

    private static final long MAX_IMAGE_SIZE = 10L * 1024 * 1024;
    private static final long MAX_VIDEO_SIZE = 100L * 1024 * 1024;
    private static final long MAX_DOCUMENT_SIZE = 20L * 1024 * 1024;

    private final ProductMediaRepository productMediaRepository;
    private final ProductRepository productRepository;
    private final StorageService storageService;

    @Override
    @Transactional(readOnly = true)
    public List<ProductMediaResponse> getProductMedia(Long productId) {

        ensureProductExists(productId);

        return productMediaRepository
                .findByProductIdAndActiveTrueOrderByDisplayOrderAsc(productId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductMediaResponse getPrimaryMedia(Long productId) {

        ensureProductExists(productId);

        return productMediaRepository
                .findByProductIdAndIsPrimaryTrueAndActiveTrue(productId)
                .map(this::mapToResponse)
                .orElse(null);
    }

    @Override
    @Transactional
    public ProductMediaResponse uploadMedia(
            Long productId,
            MultipartFile file,
            MediaType mediaType,
            boolean primary,
            String altText) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Product not found with id: " + productId
                ));

        validateFile(file, mediaType);

        String folder = getStorageFolder(mediaType);

        StoredFile storedFile = null;

        try {

            storedFile = storageService.store(
                    productId,
                    file,
                    folder
            );

            if (primary) {
                clearExistingPrimary(productId);
            }

            int nextDisplayOrder =
                    getNextDisplayOrder(productId);

            ProductMedia media = ProductMedia.builder()
                    .product(product)
                    .mediaType(mediaType)
                    .fileName(storedFile.originalFileName())
                    .storageKey(storedFile.storageKey())
                    .mediaUrl(storedFile.mediaUrl())
                    .contentType(storedFile.contentType())
                    .fileSize(storedFile.fileSize())
                    .isPrimary(primary)
                    .displayOrder(nextDisplayOrder)
                    .altText(
                            altText == null || altText.isBlank()
                                    ? storedFile.originalFileName()
                                    : altText.trim()
                    )
                    .active(true)
                    .build();

            ProductMedia saved =
                    productMediaRepository.save(media);

            return mapToResponse(saved);

        } catch (RuntimeException ex) {

            /*
             * If physical storage succeeded but the database
             * operation failed, remove the orphaned file.
             */
            if (storedFile != null) {
                try {
                    storageService.delete(
                            storedFile.storageKey()
                    );
                } catch (RuntimeException cleanupException) {
                    ex.addSuppressed(cleanupException);
                }
            }

            throw ex;
        }
    }

    
    @Override
    @Transactional
    public void setPrimaryMedia(
            Long productId,
            Long mediaId) {

        // Make sure the product exists
        ensureProductExists(productId);

        // Find the requested media belonging to this product
        ProductMedia media =
                productMediaRepository
                        .findByIdAndProductId(mediaId, productId)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Media not found for product"
                        ));

        // Do not allow inactive media to become primary
        if (!Boolean.TRUE.equals(media.getActive())) {
            throw new IllegalArgumentException(
                    "Inactive media cannot be set as primary"
            );
        }

        // Find the current primary media, if one exists
        productMediaRepository
                .findByProductIdAndIsPrimaryTrueAndActiveTrue(productId)
                .ifPresent(existingPrimary -> {

                    // If the requested media is already primary,
                    // there is nothing to change.
                    if (!existingPrimary.getId().equals(mediaId)) {
                        existingPrimary.setIsPrimary(false);
                        productMediaRepository.save(existingPrimary);
                    }
                });

        // Set requested media as primary
        media.setIsPrimary(true);

        productMediaRepository.save(media);
    }
    
    
    
    @Override
    @Transactional
    public void deleteMedia(
            Long productId,
            Long mediaId) {

        ProductMedia media =
                productMediaRepository
                        .findByIdAndProductId(mediaId, productId)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Media not found for product"
                        ));

        String storageKey = media.getStorageKey();

        productMediaRepository.delete(media);

        /*
         * Delete the physical file after removing the DB record.
         */
        storageService.delete(storageKey);
    }

    private void clearExistingPrimary(Long productId) {

        productMediaRepository
                .findByProductIdAndIsPrimaryTrueAndActiveTrue(productId)
                .ifPresent(existingPrimary -> {

                    existingPrimary.setIsPrimary(false);

                    productMediaRepository.save(existingPrimary);
                });
    }

    private int getNextDisplayOrder(Long productId) {

        return (int) productMediaRepository
                .countByProductIdAndActiveTrue(productId) + 1;
    }

    private String getStorageFolder(MediaType mediaType) {

        return switch (mediaType) {

            case IMAGE -> "images";

            case VIDEO -> "videos";

            case DOCUMENT -> "documents";
        };
    }

    private void validateFile(
            MultipartFile file,
            MediaType mediaType) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please select a file"
            );
        }

        String contentType = file.getContentType();

        if (contentType == null || contentType.isBlank()) {
            throw new IllegalArgumentException(
                    "Unable to determine file type"
            );
        }

        contentType = contentType.toLowerCase();

        long size = file.getSize();

        switch (mediaType) {

            case IMAGE -> {

                if (!IMAGE_TYPES.contains(contentType)) {
                    throw new IllegalArgumentException(
                            "Unsupported image type. Allowed: JPEG, PNG, WEBP, AVIF"
                    );
                }

                if (size > MAX_IMAGE_SIZE) {
                    throw new IllegalArgumentException(
                            "Image size cannot exceed 10 MB"
                    );
                }
            }

            case VIDEO -> {

                if (!VIDEO_TYPES.contains(contentType)) {
                    throw new IllegalArgumentException(
                            "Unsupported video type. Allowed: MP4, WEBM"
                    );
                }

                if (size > MAX_VIDEO_SIZE) {
                    throw new IllegalArgumentException(
                            "Video size cannot exceed 100 MB"
                    );
                }
            }

            case DOCUMENT -> {

                if (!DOCUMENT_TYPES.contains(contentType)) {
                    throw new IllegalArgumentException(
                            "Unsupported document type. Only PDF is allowed"
                    );
                }

                if (size > MAX_DOCUMENT_SIZE) {
                    throw new IllegalArgumentException(
                            "Document size cannot exceed 20 MB"
                    );
                }
            }
        }
    }

    private void ensureProductExists(Long productId) {

        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException(
                    "Product not found with id: " + productId
            );
        }
    }

    private ProductMediaResponse mapToResponse(
            ProductMedia media) {

        return new ProductMediaResponse(
                media.getId(),
                media.getProduct().getId(),
                media.getMediaType(),
                media.getFileName(),
                media.getStorageKey(),
                media.getMediaUrl(),
                media.getContentType(),
                media.getFileSize(),
                media.getIsPrimary(),
                media.getDisplayOrder(),
                media.getAltText(),
                media.getActive(),
                media.getCreatedAt(),
                media.getUpdatedAt()
        );
    }
}