package com.parammart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.parammart.dto.request.AddressRequest;
import com.parammart.dto.response.ApiResponse;
import com.parammart.entity.Address;
import com.parammart.service.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/addresses")
@PreAuthorize("hasRole('CUSTOMER')")
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Address>> addAddress(
            @Valid @RequestBody AddressRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "Address added successfully.",
                        service.addAddress(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Address>>> getAddresses() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Addresses fetched successfully.",
                        service.getMyAddresses()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Address>> updateAddress(
            @PathVariable Long id,
            @Valid @RequestBody AddressRequest request) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Address updated successfully.",
                        service.updateAddress(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteAddress(
            @PathVariable Long id) {

        service.deleteAddress(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Address deleted successfully.",
                        null));
    }

    @PutMapping("/default/{id}")
    public ResponseEntity<ApiResponse<Address>> setDefaultAddress(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Default address updated successfully.",
                        service.setDefaultAddress(id)));
    }
}