package com.parammart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.parammart.dto.response.ProductMediaResponse;
import com.parammart.enums.MediaType;
import com.parammart.service.ProductMediaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products/{productId}/media")
@RequiredArgsConstructor
public class ProductMediaController {

    private final ProductMediaService productMediaService;

    // ---------------------------------------------------------
    // GET ALL MEDIA
    // ADMIN / MANAGER / EMPLOYEE / CUSTOMER
    // ---------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<ProductMediaResponse>> getProductMedia(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                productMediaService.getProductMedia(productId)
        );
    }

    // ---------------------------------------------------------
    // GET PRIMARY MEDIA
    // ADMIN / MANAGER / EMPLOYEE / CUSTOMER
    // ---------------------------------------------------------
    @GetMapping("/primary")
    public ResponseEntity<ProductMediaResponse> getPrimaryMedia(
            @PathVariable Long productId) {

        ProductMediaResponse response =
                productMediaService.getPrimaryMedia(productId);

        if (response == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }

    // ---------------------------------------------------------
    // UPLOAD MEDIA
    // ADMIN + MANAGER
    // PRODUCT_UPDATE permission
    // ---------------------------------------------------------
    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ProductMediaResponse> uploadMedia(
            @PathVariable Long productId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("mediaType") MediaType mediaType,
            @RequestParam(value = "primary", defaultValue = "false") boolean primary,
            @RequestParam(value = "altText", required = false) String altText) {

        ProductMediaResponse response =
                productMediaService.uploadMedia(
                        productId,
                        file,
                        mediaType,
                        primary,
                        altText
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ---------------------------------------------------------
    // SET PRIMARY IMAGE
    // ADMIN + MANAGER
    // PRODUCT_UPDATE permission
    // ---------------------------------------------------------
    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
    @PutMapping("/{mediaId}/primary")
    public ResponseEntity<Void> setPrimaryMedia(
            @PathVariable Long productId,
            @PathVariable Long mediaId) {

        productMediaService.setPrimaryMedia(productId, mediaId);

        return ResponseEntity.noContent().build();
    }

    // ---------------------------------------------------------
    // DELETE MEDIA
    // ADMIN ONLY
    // PRODUCT_DELETE permission
    // ---------------------------------------------------------
    @PreAuthorize("hasAuthority('PRODUCT_DELETE')")
    @DeleteMapping("/{mediaId}")
    public ResponseEntity<Void> deleteMedia(
            @PathVariable Long productId,
            @PathVariable Long mediaId) {

        productMediaService.deleteMedia(productId, mediaId);

        return ResponseEntity.noContent().build();
    }
}