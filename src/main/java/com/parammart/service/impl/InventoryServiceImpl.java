package com.parammart.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parammart.dto.request.InventoryRequest;
import com.parammart.dto.response.InventoryResponse;
import com.parammart.entity.Inventory;
import com.parammart.entity.Product;
import com.parammart.exception.ResourceAlreadyExistsException;
import com.parammart.exception.ResourceNotFoundException;
import com.parammart.repository.InventoryRepository;
import com.parammart.repository.ProductRepository;
import com.parammart.service.InventoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    // =========================================================
    // CREATE INVENTORY
    // ADMIN + MANAGER
    // =========================================================

    @Override
    public InventoryResponse createInventory(
            InventoryRequest request) {

        validateRequest(request);

        Product product =
                productRepository.findById(request.getProductId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found with id: "
                                                + request.getProductId()));

        if (inventoryRepository.existsByProductId(
                request.getProductId())) {

            throw new ResourceAlreadyExistsException(
                    "Inventory already exists for product id: "
                            + request.getProductId());
        }

        Inventory inventory = new Inventory();

        inventory.setProduct(product);
        inventory.setAvailableStock(
                request.getAvailableStock());
        inventory.setReservedStock(0);
        inventory.setMinimumStock(
                request.getMinimumStock());
        inventory.setMaximumStock(
                request.getMaximumStock());
        inventory.setWarehouseLocation(
                request.getWarehouseLocation());
        inventory.setActive(true);

        Inventory saved =
                inventoryRepository.save(inventory);

        return mapToResponse(saved);
    }

    // =========================================================
    // GET ALL INVENTORY
    // ADMIN + MANAGER + EMPLOYEE + CUSTOMER
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getAllInventory() {

        return inventoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================================================
    // GET BY ID
    // ADMIN + MANAGER + EMPLOYEE + CUSTOMER
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public InventoryResponse getInventoryById(Long id) {

        Inventory inventory =
                inventoryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Inventory not found with id: "
                                                + id));

        return mapToResponse(inventory);
    }

    // =========================================================
    // GET BY PRODUCT ID
    // ADMIN + MANAGER + EMPLOYEE + CUSTOMER
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public InventoryResponse getInventoryByProductId(
            Long productId) {

        Inventory inventory =
                inventoryRepository.findByProductId(productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Inventory not found for product id: "
                                                + productId));

        return mapToResponse(inventory);
    }

    // =========================================================
    // UPDATE INVENTORY
    // ADMIN ONLY
    // =========================================================

    @Override
    public InventoryResponse updateInventory(
            Long id,
            InventoryRequest request) {

        validateRequest(request);

        Inventory inventory =
                inventoryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Inventory not found with id: "
                                                + id));

        Product product =
                productRepository.findById(
                        request.getProductId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found with id: "
                                                + request.getProductId()));

        if (!inventory.getProduct().getId()
                .equals(request.getProductId())) {

            if (inventoryRepository.existsByProductId(
                    request.getProductId())) {

                throw new ResourceAlreadyExistsException(
                        "Inventory already exists for product id: "
                                + request.getProductId());
            }
        }

        inventory.setProduct(product);

        inventory.setAvailableStock(
                request.getAvailableStock());

        inventory.setMinimumStock(
                request.getMinimumStock());

        inventory.setMaximumStock(
                request.getMaximumStock());

        inventory.setWarehouseLocation(
                request.getWarehouseLocation());

        Inventory updated =
                inventoryRepository.save(inventory);

        return mapToResponse(updated);
    }

    // =========================================================
    // DELETE INVENTORY
    // ADMIN ONLY
    // =========================================================

    @Override
    public void deleteInventory(Long id) {

        Inventory inventory =
                inventoryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Inventory not found with id: "
                                                + id));

        inventoryRepository.delete(inventory);
    }

    // =========================================================
    // STOCK IN
    // ADMIN + MANAGER
    // =========================================================

    @Override
    public InventoryResponse stockIn(
            Long productId,
            Integer quantity) {

        validatePositiveQuantity(quantity);

        Inventory inventory =
                inventoryRepository.findByProductId(productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Inventory not found for product id: "
                                                + productId));

        int currentStock =
                inventory.getAvailableStock();

        int maximumStock =
                inventory.getMaximumStock();

        if (currentStock + quantity > maximumStock) {

            throw new IllegalArgumentException(
                    "Stock cannot exceed maximum stock of "
                            + maximumStock);
        }

        inventory.setAvailableStock(
                currentStock + quantity);

        Inventory updated =
                inventoryRepository.save(inventory);

        return mapToResponse(updated);
    }

    // =========================================================
    // STOCK OUT
    // ADMIN + MANAGER
    // =========================================================

    @Override
    public InventoryResponse stockOut(
            Long productId,
            Integer quantity) {

        validatePositiveQuantity(quantity);

        Inventory inventory =
                inventoryRepository.findByProductId(productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Inventory not found for product id: "
                                                + productId));

        int currentStock =
                inventory.getAvailableStock();

        if (quantity > currentStock) {

            throw new IllegalArgumentException(
                    "Insufficient stock. Available stock: "
                            + currentStock);
        }

        inventory.setAvailableStock(
                currentStock - quantity);

        Inventory updated =
                inventoryRepository.save(inventory);

        return mapToResponse(updated);
    }

    // =========================================================
    // LOW STOCK
    // ADMIN + MANAGER
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getLowStockProducts() {

        return inventoryRepository
                .findByAvailableStockLessThanEqual(10)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================================================
    // VALIDATION
    // =========================================================

    private void validateRequest(
            InventoryRequest request) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "Inventory request cannot be null");
        }

        if (request.getProductId() == null) {
            throw new IllegalArgumentException(
                    "Product ID is required");
        }

        if (request.getAvailableStock() == null) {
            throw new IllegalArgumentException(
                    "Available stock is required");
        }

        if (request.getMinimumStock() == null) {
            throw new IllegalArgumentException(
                    "Minimum stock is required");
        }

        if (request.getMaximumStock() == null) {
            throw new IllegalArgumentException(
                    "Maximum stock is required");
        }

        if (request.getAvailableStock() < 0) {
            throw new IllegalArgumentException(
                    "Available stock cannot be negative");
        }

        if (request.getMinimumStock() < 0) {
            throw new IllegalArgumentException(
                    "Minimum stock cannot be negative");
        }

        if (request.getMaximumStock() <= 0) {
            throw new IllegalArgumentException(
                    "Maximum stock must be greater than zero");
        }

        if (request.getMinimumStock()
                > request.getMaximumStock()) {

            throw new IllegalArgumentException(
                    "Minimum stock cannot be greater than maximum stock");
        }

        if (request.getAvailableStock()
                > request.getMaximumStock()) {

            throw new IllegalArgumentException(
                    "Available stock cannot be greater than maximum stock");
        }
    }

    // =========================================================
    // QUANTITY VALIDATION
    // =========================================================

    private void validatePositiveQuantity(
            Integer quantity) {

        if (quantity == null || quantity <= 0) {

            throw new IllegalArgumentException(
                    "Quantity must be greater than zero");
        }
    }

    // =========================================================
    // ENTITY -> DTO
    // =========================================================

    private InventoryResponse mapToResponse(
            Inventory inventory) {

        Product product =
                inventory.getProduct();

        return new InventoryResponse(
                inventory.getId(),

                product != null
                        ? product.getId()
                        : null,

                product != null
                        ? product.getName()
                        : null,

                product != null
                        ? product.getSku()
                        : null,

                inventory.getAvailableStock(),

                inventory.getReservedStock(),

                inventory.getMinimumStock(),

                inventory.getMaximumStock(),

                inventory.getWarehouseLocation(),

                inventory.getActive(),

                inventory.getCreatedAt(),

                inventory.getUpdatedAt()
        );
    }
}