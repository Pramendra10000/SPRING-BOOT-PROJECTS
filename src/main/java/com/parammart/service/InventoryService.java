package com.parammart.service;

import java.util.List;

import com.parammart.dto.request.InventoryRequest;
import com.parammart.entity.Inventory;

public interface InventoryService {

    Inventory createInventory(InventoryRequest request);

    List<Inventory> getAllInventory();

    Inventory getInventoryById(Long id);

    Inventory getInventoryByProductId(Long productId);

    Inventory updateInventory(Long id, InventoryRequest request);

    void deleteInventory(Long id);

    Inventory stockIn(Long productId, Integer quantity);

    Inventory stockOut(Long productId, Integer quantity);

    List<Inventory> getLowStockProducts();
}