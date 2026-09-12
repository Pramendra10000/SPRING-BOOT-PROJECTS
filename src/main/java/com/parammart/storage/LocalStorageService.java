package com.parammart.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LocalStorageService implements StorageService {

    private final Path rootLocation;
    private final String publicBaseUrl;

    public LocalStorageService(
            @Value("${app.storage.local.root:uploads}") String rootLocation,
            @Value("${app.storage.local.public-base-url:http://localhost:8080/media}")
            String publicBaseUrl) {

        this.rootLocation = Paths.get(rootLocation)
                .toAbsolutePath()
                .normalize();

        this.publicBaseUrl = publicBaseUrl.replaceAll("/+$", "");
    }

    @Override
    public StoredFile store(
            Long productId,
            MultipartFile file,
            String mediaFolder) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        String originalFileName = StringUtils.cleanPath(
                file.getOriginalFilename() == null
                        ? "file"
                        : file.getOriginalFilename()
        );

        String extension = getExtension(originalFileName);

        String generatedFileName = UUID.randomUUID()
                + (extension.isBlank() ? "" : "." + extension);

        String folder = sanitizeFolder(mediaFolder);

        String storageKey =
                "products/"
                        + productId
                        + "/"
                        + folder
                        + "/"
                        + generatedFileName;

        Path targetPath = rootLocation.resolve(storageKey)
                .normalize();

        /*
         * Security protection:
         * Make sure the final path cannot escape
         * the configured storage directory.
         */
        if (!targetPath.startsWith(rootLocation)) {
            throw new IllegalArgumentException("Invalid storage path");
        }

        try {
            Files.createDirectories(targetPath.getParent());

            try (InputStream inputStream = file.getInputStream()) {

                Files.copy(
                        inputStream,
                        targetPath,
                        StandardCopyOption.REPLACE_EXISTING
                );
            }

        } catch (IOException ex) {
            throw new IllegalStateException(
                    "Failed to store file: " + originalFileName,
                    ex
            );
        }

        String mediaUrl =
                publicBaseUrl
                        + "/"
                        + storageKey;

        return new StoredFile(
                storageKey,
                mediaUrl,
                originalFileName,
                file.getContentType(),
                file.getSize()
        );
    }

    @Override
    public void delete(String storageKey) {
        if (storageKey == null || storageKey.isBlank()) {
            return;
        }

        Path targetPath = rootLocation.resolve(storageKey)
                .normalize();

        if (!targetPath.startsWith(rootLocation)) {
            throw new IllegalArgumentException("Invalid storage path");
        }

        System.out.println("========================================");
        System.out.println("DELETE STORAGE KEY : " + storageKey);
        System.out.println("DELETE TARGET PATH : " + targetPath);
        System.out.println("FILE EXISTS BEFORE : " + Files.exists(targetPath));
        System.out.println("ROOT LOCATION     : " + rootLocation);
        System.out.println("========================================");

        try {
            Files.deleteIfExists(targetPath);

            System.out.println(
                    "FILE EXISTS AFTER  : " + Files.exists(targetPath)
            );

        } catch (IOException ex) {
            throw new IllegalStateException(
                    "Failed to delete stored file: " + storageKey,
                    ex
            );
        }
    }

    @Override
    public boolean exists(String storageKey) {

        if (storageKey == null || storageKey.isBlank()) {
            return false;
        }

        Path targetPath = rootLocation.resolve(storageKey)
                .normalize();

        if (!targetPath.startsWith(rootLocation)) {
            return false;
        }

        return Files.exists(targetPath);
    }

    private String getExtension(String fileName) {

        int lastDot = fileName.lastIndexOf('.');

        if (lastDot < 0 || lastDot == fileName.length() - 1) {
            return "";
        }

        return fileName.substring(lastDot + 1)
                .toLowerCase();
    }

    private String sanitizeFolder(String folder) {

        if (folder == null || folder.isBlank()) {
            throw new IllegalArgumentException(
                    "Storage folder cannot be empty"
            );
        }

        String sanitized = folder
                .replace("\\", "")
                .replace("/", "");

        if (!sanitized.matches("[a-zA-Z0-9_-]+")) {
            throw new IllegalArgumentException(
                    "Invalid storage folder"
            );
        }

        return sanitized;
    }
}