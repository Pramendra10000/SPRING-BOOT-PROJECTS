package com.parammart.storage;

public record StoredFile(
        String storageKey,
        String mediaUrl,
        String originalFileName,
        String contentType,
        long fileSize
) {
}