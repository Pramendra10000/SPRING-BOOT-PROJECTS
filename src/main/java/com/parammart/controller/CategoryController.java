package com.parammart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parammart.entity.Category;
import com.parammart.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    // =========================================================
    // PUBLIC CATALOG APIs
    // =========================================================

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {

        return ResponseEntity.ok(
            service.getAllCategories()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
            service.getCategoryById(id)
        );
    }

    // =========================================================
    // CATEGORY MANAGEMENT
    // =========================================================

    @PostMapping
    @PreAuthorize("hasAuthority('CATEGORY_CREATE')")
    public ResponseEntity<Category> create(
            @RequestBody Category category) {

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(service.createCategory(category));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY_UPDATE')")
    public ResponseEntity<Category> update(
            @PathVariable Long id,
            @RequestBody Category category) {

        return ResponseEntity.ok(
            service.updateCategory(id, category)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY_DELETE')")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        service.deleteCategory(id);

        return ResponseEntity.ok(
            "Category deleted successfully"
        );
    }
}