package com.parammart.entity;

import java.time.LocalDateTime;

import com.parammart.enums.MediaType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
	    name = "product_media",
	    indexes = {
	        @Index(
	            name = "idx_product_media_product_active_order",
	            columnList = "product_id, active, display_order"
	        ),
	        @Index(
	            name = "idx_product_media_product_primary_active",
	            columnList = "product_id, is_primary, active"
	        ),
	        @Index(
	            name = "idx_product_media_type",
	            columnList = "media_type"
	        )
	    }
	)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Product to which this media belongs.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "product_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_product_media_product")
    )
    private Product product;

    /**
     * IMAGE / VIDEO / DOCUMENT
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "media_type", nullable = false, length = 20)
    private MediaType mediaType;

    /**
     * Original file name supplied by the user.
     */
    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    /**
     * Unique path/key used inside object storage.
     *
     * Example:
     * products/1/images/550e8400-e29b-41d4-a716-446655440000.webp
     */
    @Column(name = "storage_key", nullable = false, unique = true, length = 500)
    private String storageKey;

    /**
     * Public URL or delivery URL of the media.
     */
    @Column(name = "media_url", length = 1000)
    private String mediaUrl;

    /**
     * MIME type.
     *
     * Examples:
     * image/jpeg
     * image/webp
     * video/mp4
     */
    @Column(name = "content_type", nullable = false, length = 100)
    private String contentType;

    /**
     * Size of the physical file in bytes.
     */
    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    /**
     * Main image/video for the product.
     */
    @Column(name = "is_primary", nullable = false)
    @Builder.Default
    private Boolean isPrimary = false;

    /**
     * Ordering of media in the product gallery.
     */
    @Column(name = "display_order", nullable = false)
    @Builder.Default
    private Integer displayOrder = 0;

    /**
     * Accessibility / SEO description.
     */
    @Column(name = "alt_text", length = 500)
    private String altText;

    /**
     * Soft-enable/disable media without deleting its database record.
     */
    @Column(name = "active", nullable = false)
    @Builder.Default
    private Boolean active = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;

        if (isPrimary == null) {
            isPrimary = false;
        }

        if (displayOrder == null) {
            displayOrder = 0;
        }

        if (active == null) {
            active = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}