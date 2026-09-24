package com.parammart.service;

import java.util.List;

import com.parammart.dto.request.InventoryRequest;
import com.parammart.dto.response.InventoryResponse;

public interface InventoryService {

    InventoryResponse createInventory(InventoryRequest request);

    List<InventoryResponse> getAllInventory();

    InventoryResponse getInventoryById(Long id);

    InventoryResponse getInventoryByProductId(Long productId);

    InventoryResponse updateInventory(Long id, InventoryRequest request);

    void deleteInventory(Long id);

    InventoryResponse stockIn(Long productId, Integer quantity);

    InventoryResponse stockOut(Long productId, Integer quantity);

    List<InventoryResponse> getLowStockProducts();
}