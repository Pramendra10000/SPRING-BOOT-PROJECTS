package com.parammart.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.parammart.dto.request.InventoryRequest;
import com.parammart.entity.Inventory;
import com.parammart.entity.Product;
import com.parammart.exception.BadRequestException;
import com.parammart.exception.ResourceNotFoundException;
import com.parammart.repository.InventoryRepository;
import com.parammart.repository.ProductRepository;
import com.parammart.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public InventoryServiceImpl(
            InventoryRepository inventoryRepository,
            ProductRepository productRepository) {

        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Inventory createInventory(InventoryRequest request) {

        if (inventoryRepository.existsByProductId(request.getProductId())) {
            throw new BadRequestException("Inventory already exists for this product.");
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        Inventory inventory = new Inventory();

        inventory.setProduct(product);
        inventory.setAvailableStock(request.getAvailableStock());
        inventory.setMinimumStock(request.getMinimumStock());
        inventory.setMaximumStock(request.getMaximumStock());
        inventory.setWarehouseLocation(request.getWarehouseLocation());
        inventory.setReservedStock(0);
        inventory.setActive(true);

        return inventoryRepository.save(inventory);
    }

    @Override
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    @Override
    public Inventory getInventoryById(Long id) {

        return inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found"));
    }

    @Override
    public Inventory getInventoryByProductId(Long productId) {

        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found"));
    }

    @Override
    public Inventory updateInventory(Long id, InventoryRequest request) {

        Inventory inventory = getInventoryById(id);

        inventory.setAvailableStock(request.getAvailableStock());
        inventory.setMinimumStock(request.getMinimumStock());
        inventory.setMaximumStock(request.getMaximumStock());
        inventory.setWarehouseLocation(request.getWarehouseLocation());

        return inventoryRepository.save(inventory);
    }

    @Override
    public void deleteInventory(Long id) {

        Inventory inventory = getInventoryById(id);

        inventoryRepository.delete(inventory);
    }

    @Override
    public Inventory stockIn(Long productId, Integer quantity) {

        Inventory inventory = getInventoryByProductId(productId);

        inventory.setAvailableStock(
                inventory.getAvailableStock() + quantity);

        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory stockOut(Long productId, Integer quantity) {

        Inventory inventory = getInventoryByProductId(productId);

        if (inventory.getAvailableStock() < quantity) {
            throw new BadRequestException("Insufficient stock.");
        }

        inventory.setAvailableStock(
                inventory.getAvailableStock() - quantity);

        return inventoryRepository.save(inventory);
    }

    @Override
    public List<Inventory> getLowStockProducts() {

        return inventoryRepository.findByAvailableStockLessThanEqual(10);
    }

}