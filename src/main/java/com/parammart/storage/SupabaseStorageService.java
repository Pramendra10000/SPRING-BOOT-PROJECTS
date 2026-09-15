package com.parammart.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
@ConditionalOnProperty(
        name = "app.storage.supabase.enabled",
        havingValue = "true"
)
public class SupabaseStorageService implements StorageService {

    private final S3Client s3Client;
    private final String bucket;
    private final String publicBaseUrl;

    public SupabaseStorageService(

            @Value("${app.storage.supabase.endpoint}")
            String endpoint,

            @Value("${app.storage.supabase.region}")
            String region,

            @Value("${app.storage.supabase.bucket}")
            String bucket,

            @Value("${app.storage.supabase.public-base-url}")
            String publicBaseUrl) {

        /*
         * ======================================================
         * READ SUPABASE CREDENTIALS DIRECTLY FROM ENVIRONMENT
         * ======================================================
         */

        String accessKey =
                System.getenv("SUPABASE_STORAGE_ACCESS_KEY");

        String secretKey =
                System.getenv("SUPABASE_STORAGE_SECRET_KEY");

        /*
         * ======================================================
         * TEMPORARY DIAGNOSTIC
         * ======================================================
         *
         * Only TRUE/FALSE is printed.
         * Actual credentials are NEVER printed.
         */

        System.out.println(
                "========== SUPABASE ENV CHECK =========="
        );

        System.out.println(
                "SUPABASE_STORAGE_ACCESS_KEY present: "
                        + (
                            accessKey != null
                            && !accessKey.isBlank()
                        )
        );

        System.out.println(
                "SUPABASE_STORAGE_SECRET_KEY present: "
                        + (
                            secretKey != null
                            && !secretKey.isBlank()
                        )
        );

        System.out.println(
                "========================================"
        );

        /*
         * ======================================================
         * CREDENTIAL VALIDATION
         * ======================================================
         */

        if (accessKey == null || accessKey.isBlank()) {

            throw new IllegalStateException(
                    "SUPABASE_STORAGE_ACCESS_KEY is not configured"
            );
        }

        if (secretKey == null || secretKey.isBlank()) {

            throw new IllegalStateException(
                    "SUPABASE_STORAGE_SECRET_KEY is not configured"
            );
        }

        /*
         * ======================================================
         * STORAGE CONFIGURATION
         * ======================================================
         */

        this.bucket = bucket;

        this.publicBaseUrl =
                publicBaseUrl.replaceAll("/+$", "");

        /*
         * ======================================================
         * AWS/S3 CREDENTIALS
         * ======================================================
         */

        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(
                        accessKey,
                        secretKey
                );

        /*
         * ======================================================
         * S3 CONFIGURATION
         * ======================================================
         *
         * Supabase Storage's S3-compatible API works with
         * path-style access.
         */

        S3Configuration s3Configuration =
                S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build();

        /*
         * ======================================================
         * CREATE S3 CLIENT
         * ======================================================
         */

        this.s3Client =
                S3Client.builder()
                        .endpointOverride(
                                URI.create(endpoint)
                        )
                        .region(
                                Region.of(region)
                        )
                        .credentialsProvider(
                                StaticCredentialsProvider.create(
                                        credentials
                                )
                        )
                        .serviceConfiguration(
                                s3Configuration
                        )
                        .build();
    }

    /*
     * ==========================================================
     * STORE FILE
     * ==========================================================
     */

    @Override
    public StoredFile store(
            Long productId,
            MultipartFile file,
            String mediaFolder) {

        if (file == null || file.isEmpty()) {

            throw new IllegalArgumentException(
                    "File cannot be empty"
            );
        }

        /*
         * ------------------------------------------------------
         * ORIGINAL FILE NAME
         * ------------------------------------------------------
         */

        String originalFileName =
                StringUtils.cleanPath(
                        file.getOriginalFilename() == null
                                ? "file"
                                : file.getOriginalFilename()
                );

        /*
         * ------------------------------------------------------
         * FILE EXTENSION
         * ------------------------------------------------------
         */

        String extension =
                getExtension(originalFileName);

        /*
         * ------------------------------------------------------
         * GENERATE UNIQUE FILE NAME
         * ------------------------------------------------------
         */

        String generatedFileName =
                UUID.randomUUID()
                        + (
                            extension.isBlank()
                                    ? ""
                                    : "." + extension
                        );

        /*
         * ------------------------------------------------------
         * VALIDATE MEDIA FOLDER
         * ------------------------------------------------------
         */

        String folder =
                sanitizeFolder(mediaFolder);

        /*
         * ------------------------------------------------------
         * STORAGE KEY
         * ------------------------------------------------------
         *
         * Example:
         *
         * products/1/images/550e8400-e29b-41d4-a716-446655440000.jpg
         *
         */

        String storageKey =
                "products/"
                        + productId
                        + "/"
                        + folder
                        + "/"
                        + generatedFileName;

        /*
         * ------------------------------------------------------
         * CONTENT TYPE
         * ------------------------------------------------------
         */

        String contentType =
                file.getContentType();

        if (contentType == null
                || contentType.isBlank()) {

            contentType =
                    "application/octet-stream";
        }

        /*
         * ------------------------------------------------------
         * CREATE PUT REQUEST
         * ------------------------------------------------------
         */

        PutObjectRequest request =
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(storageKey)
                        .contentType(contentType)
                        .contentLength(file.getSize())
                        .build();

        /*
         * ------------------------------------------------------
         * UPLOAD FILE
         * ------------------------------------------------------
         */

        try (
                InputStream inputStream =
                        file.getInputStream()
        ) {

            s3Client.putObject(
                    request,
                    RequestBody.fromInputStream(
                            inputStream,
                            file.getSize()
                    )
            );

        } catch (IOException ex) {

            throw new IllegalStateException(
                    "Failed to upload file to Supabase Storage: "
                            + originalFileName,
                    ex
            );
        }

        /*
         * ------------------------------------------------------
         * PUBLIC MEDIA URL
         * ------------------------------------------------------
         */

        String mediaUrl =
                publicBaseUrl
                        + "/"
                        + storageKey;

        /*
         * ------------------------------------------------------
         * RETURN STORED FILE INFORMATION
         * ------------------------------------------------------
         */

        return new StoredFile(
                storageKey,
                mediaUrl,
                originalFileName,
                contentType,
                file.getSize()
        );
    }

    /*
     * ==========================================================
     * DELETE FILE
     * ==========================================================
     */

    @Override
    public void delete(String storageKey) {

        if (storageKey == null
                || storageKey.isBlank()) {

            return;
        }

        DeleteObjectRequest request =
                DeleteObjectRequest.builder()
                        .bucket(bucket)
                        .key(storageKey)
                        .build();

        try {

            s3Client.deleteObject(request);

        } catch (S3Exception ex) {

            throw new IllegalStateException(
                    "Failed to delete file from Supabase Storage: "
                            + storageKey,
                    ex
            );
        }
    }

    /*
     * ==========================================================
     * CHECK FILE EXISTS
     * ==========================================================
     */

    @Override
    public boolean exists(String storageKey) {

        if (storageKey == null
                || storageKey.isBlank()) {

            return false;
        }

        HeadObjectRequest request =
                HeadObjectRequest.builder()
                        .bucket(bucket)
                        .key(storageKey)
                        .build();

        try {

            s3Client.headObject(request);

            return true;

        } catch (NoSuchKeyException ex) {

            return false;

        } catch (S3Exception ex) {

            if (ex.statusCode() == 404) {

                return false;
            }

            throw new IllegalStateException(
                    "Failed to check Supabase Storage file: "
                            + storageKey,
                    ex
            );
        }
    }

    /*
     * ==========================================================
     * GET FILE EXTENSION
     * ==========================================================
     */

    private String getExtension(
            String fileName) {

        int lastDot =
                fileName.lastIndexOf('.');

        if (lastDot < 0
                || lastDot == fileName.length() - 1) {

            return "";
        }

        return fileName
                .substring(lastDot + 1)
                .toLowerCase();
    }

    /*
     * ==========================================================
     * SANITIZE MEDIA FOLDER
     * ==========================================================
     */

    private String sanitizeFolder(
            String folder) {

        if (folder == null
                || folder.isBlank()) {

            throw new IllegalArgumentException(
                    "Storage folder cannot be empty"
            );
        }

        String sanitized =
                folder
                        .replace("\\", "")
                        .replace("/", "");

        if (!sanitized.matches(
                "[a-zA-Z0-9_-]+")) {

            throw new IllegalArgumentException(
                    "Invalid storage folder"
            );
        }

        return sanitized;
    }
}