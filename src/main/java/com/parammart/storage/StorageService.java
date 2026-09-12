package com.parammart.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    StoredFile store(
            Long productId,
            MultipartFile file,
            String mediaFolder
    );

    void delete(String storageKey);

    boolean exists(String storageKey);
}