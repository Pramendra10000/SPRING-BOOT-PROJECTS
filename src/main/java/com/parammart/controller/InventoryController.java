package com.parammart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.parammart.dto.request.InventoryRequest;
import com.parammart.dto.response.ApiResponse;
import com.parammart.dto.response.InventoryResponse;
import com.parammart.service.InventoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/inventory")
@Validated
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(
            InventoryService inventoryService) {

        this.inventoryService = inventoryService;
    }

    // =========================================================
    // CREATE INVENTORY
    // ADMIN + MANAGER
    // =========================================================

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<ApiResponse<InventoryResponse>>
            createInventory(
                    @Valid @RequestBody
                    InventoryRequest request) {

        InventoryResponse inventory =
                inventoryService.createInventory(request);

        ApiResponse<InventoryResponse> response =
                new ApiResponse<>(
                        true,
                        "Inventory created successfully",
                        inventory);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    // =========================================================
    // GET ALL INVENTORY
    // ADMIN + MANAGER + EMPLOYEE + CUSTOMER
    // =========================================================

    @GetMapping
    @PreAuthorize(
        "hasAnyRole('ADMIN','MANAGER','EMPLOYEE','CUSTOMER')"
    )
    public ResponseEntity<
            ApiResponse<List<InventoryResponse>>>
            getAllInventory() {

        List<InventoryResponse> inventories =
                inventoryService.getAllInventory();

        ApiResponse<List<InventoryResponse>> response =
                new ApiResponse<>(
                        true,
                        "Inventory fetched successfully",
                        inventories);

        return ResponseEntity.ok(response);
    }

    // =========================================================
    // GET INVENTORY BY ID
    // ADMIN + MANAGER + EMPLOYEE + CUSTOMER
    // =========================================================

    @GetMapping("/{id}")
    @PreAuthorize(
        "hasAnyRole('ADMIN','MANAGER','EMPLOYEE','CUSTOMER')"
    )
    public ResponseEntity<
            ApiResponse<InventoryResponse>>
            getInventoryById(
                    @PathVariable Long id) {

        InventoryResponse inventory =
                inventoryService.getInventoryById(id);

        ApiResponse<InventoryResponse> response =
                new ApiResponse<>(
                        true,
                        "Inventory fetched successfully",
                        inventory);

        return ResponseEntity.ok(response);
    }

    // =========================================================
    // GET INVENTORY BY PRODUCT ID
    // ADMIN + MANAGER + EMPLOYEE + CUSTOMER
    // =========================================================

    @GetMapping("/product/{productId}")
    @PreAuthorize(
        "hasAnyRole('ADMIN','MANAGER','EMPLOYEE','CUSTOMER')"
    )
    public ResponseEntity<
            ApiResponse<InventoryResponse>>
            getInventoryByProductId(
                    @PathVariable Long productId) {

        InventoryResponse inventory =
                inventoryService.getInventoryByProductId(
                        productId);

        ApiResponse<InventoryResponse> response =
                new ApiResponse<>(
                        true,
                        "Inventory fetched successfully",
                        inventory);

        return ResponseEntity.ok(response);
    }

    // =========================================================
    // UPDATE INVENTORY
    // ADMIN ONLY
    // =========================================================

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<
            ApiResponse<InventoryResponse>>
            updateInventory(
                    @PathVariable Long id,
                    @Valid @RequestBody
                    InventoryRequest request) {

        InventoryResponse inventory =
                inventoryService.updateInventory(
                        id,
                        request);

        ApiResponse<InventoryResponse> response =
                new ApiResponse<>(
                        true,
                        "Inventory updated successfully",
                        inventory);

        return ResponseEntity.ok(response);
    }

    // =========================================================
    // DELETE INVENTORY
    // ADMIN ONLY
    // =========================================================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>>
            deleteInventory(
                    @PathVariable Long id) {

        inventoryService.deleteInventory(id);

        ApiResponse<String> response =
                new ApiResponse<>(
                        true,
                        "Inventory deleted successfully",
                        null);

        return ResponseEntity.ok(response);
    }

    // =========================================================
    // STOCK IN
    // ADMIN + MANAGER
    // =========================================================

    @PutMapping("/stock-in/{productId}/{quantity}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<
            ApiResponse<InventoryResponse>>
            stockIn(
                    @PathVariable Long productId,
                    @PathVariable Integer quantity) {

        InventoryResponse inventory =
                inventoryService.stockIn(
                        productId,
                        quantity);

        ApiResponse<InventoryResponse> response =
                new ApiResponse<>(
                        true,
                        "Stock added successfully",
                        inventory);

        return ResponseEntity.ok(response);
    }

    // =========================================================
    // STOCK OUT
    // ADMIN + MANAGER
    // =========================================================

    @PutMapping("/stock-out/{productId}/{quantity}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<
            ApiResponse<InventoryResponse>>
            stockOut(
                    @PathVariable Long productId,
                    @PathVariable Integer quantity) {

        InventoryResponse inventory =
                inventoryService.stockOut(
                        productId,
                        quantity);

        ApiResponse<InventoryResponse> response =
                new ApiResponse<>(
                        true,
                        "Stock deducted successfully",
                        inventory);

        return ResponseEntity.ok(response);
    }

    // =========================================================
    // LOW STOCK
    // ADMIN + MANAGER
    // =========================================================

    @GetMapping("/low-stock")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<
            ApiResponse<List<InventoryResponse>>>
            getLowStockProducts() {

        List<InventoryResponse> inventories =
                inventoryService.getLowStockProducts();

        ApiResponse<List<InventoryResponse>> response =
                new ApiResponse<>(
                        true,
                        "Low stock products fetched successfully",
                        inventories);

        return ResponseEntity.ok(response);
    }
}