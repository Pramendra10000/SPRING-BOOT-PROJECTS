package com.parammart.controller;

import com.parammart.dto.ProductRequestDTO;
import com.parammart.dto.ProductResponseDTO;
import com.parammart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prod")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    
    @GetMapping("/ping")
    public String ping() {
        return "Product Controller Working";
    }
    
    public ProductController() {
        System.out.println("ProductController Loaded");
    }

    // =====================
    // ADD PRODUCT
    // =====================
    @PostMapping
    public String addProduct(@RequestBody ProductRequestDTO dto) {
        return productService.addProduct(dto);
    }

    // =====================
    // GET ALL PRODUCTS
    // =====================
    @GetMapping("/All")
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    // =====================
    // GET BY ID
    // =====================
    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    // =====================
    // UPDATE PRODUCT
    // =====================
    @PutMapping("/{id}")
    public String updateProduct(@PathVariable String id,
                                 @RequestBody ProductRequestDTO dto) {
        return productService.updateProduct(id, dto);
    }

    // =====================
    // DELETE PRODUCT
    // =====================
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id);
    }
}