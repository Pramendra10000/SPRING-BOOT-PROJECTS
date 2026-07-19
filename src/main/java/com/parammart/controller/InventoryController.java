package com.parammart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.parammart.dto.request.InventoryRequest;
import com.parammart.dto.response.ApiResponse;
import com.parammart.entity.Inventory;
import com.parammart.service.InventoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/inventory")
@Validated
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // ===========================
    // Create Inventory
    // ===========================

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<ApiResponse<Inventory>> createInventory(
            @Valid @RequestBody InventoryRequest request) {

        Inventory inventory = inventoryService.createInventory(request);

        ApiResponse<Inventory> response =
                new ApiResponse<>(true,
                        "Inventory created successfully",
                        inventory);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ===========================
    // Get All Inventory
    // ===========================

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    public ResponseEntity<ApiResponse<List<Inventory>>> getAllInventory() {

        List<Inventory> inventories =
                inventoryService.getAllInventory();

        ApiResponse<List<Inventory>> response =
                new ApiResponse<>(true,
                        "Inventory fetched successfully",
                        inventories);

        return ResponseEntity.ok(response);
    }

    // ===========================
    // Get Inventory By Id
    // ===========================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    public ResponseEntity<ApiResponse<Inventory>> getInventoryById(
            @PathVariable Long id) {

        Inventory inventory =
                inventoryService.getInventoryById(id);

        ApiResponse<Inventory> response =
                new ApiResponse<>(true,
                        "Inventory fetched successfully",
                        inventory);

        return ResponseEntity.ok(response);
    }

    // ===========================
    // Get Inventory By Product Id
    // ===========================

    @GetMapping("/product/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    public ResponseEntity<ApiResponse<Inventory>> getInventoryByProductId(
            @PathVariable Long productId) {

        Inventory inventory =
                inventoryService.getInventoryByProductId(productId);

        ApiResponse<Inventory> response =
                new ApiResponse<>(true,
                        "Inventory fetched successfully",
                        inventory);

        return ResponseEntity.ok(response);
    }

    // ===========================
    // Update Inventory
    // ===========================

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Inventory>> updateInventory(
            @PathVariable Long id,
            @Valid @RequestBody InventoryRequest request) {

        Inventory inventory =
                inventoryService.updateInventory(id, request);

        ApiResponse<Inventory> response =
                new ApiResponse<>(true,
                        "Inventory updated successfully",
                        inventory);

        return ResponseEntity.ok(response);
    }

    // ===========================
    // Delete Inventory
    // ===========================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteInventory(
            @PathVariable Long id) {

        inventoryService.deleteInventory(id);

        ApiResponse<String> response =
                new ApiResponse<>(true,
                        "Inventory deleted successfully",
                        null);

        return ResponseEntity.ok(response);
    }

    // ===========================
    // Stock In
    // ===========================

    @PutMapping("/stock-in/{productId}/{quantity}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<ApiResponse<Inventory>> stockIn(
            @PathVariable Long productId,
            @PathVariable Integer quantity) {

        Inventory inventory =
                inventoryService.stockIn(productId, quantity);

        ApiResponse<Inventory> response =
                new ApiResponse<>(true,
                        "Stock added successfully",
                        inventory);

        return ResponseEntity.ok(response);
    }

    // ===========================
    // Stock Out
    // ===========================

    @PutMapping("/stock-out/{productId}/{quantity}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<ApiResponse<Inventory>> stockOut(
            @PathVariable Long productId,
            @PathVariable Integer quantity) {

        Inventory inventory =
                inventoryService.stockOut(productId, quantity);

        ApiResponse<Inventory> response =
                new ApiResponse<>(true,
                        "Stock deducted successfully",
                        inventory);

        return ResponseEntity.ok(response);
    }

    // ===========================
    // Low Stock Products
    // ===========================

    @GetMapping("/low-stock")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<ApiResponse<List<Inventory>>> getLowStockProducts() {

        List<Inventory> inventories =
                inventoryService.getLowStockProducts();

        ApiResponse<List<Inventory>> response =
                new ApiResponse<>(true,
                        "Low stock products fetched successfully",
                        inventories);

        return ResponseEntity.ok(response);
    }

}