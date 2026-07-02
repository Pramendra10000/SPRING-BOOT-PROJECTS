package com.parammart.service;

import java.util.List;

import com.parammart.dto.ProductRequestDTO;
import com.parammart.dto.ProductResponseDTO;
import com.parammart.entity.Product;

public interface ProductService {

    
    String addProduct(ProductRequestDTO dto);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(String id);
    
    
    String updateProduct(String id, Product product);
    
    String deleteProduct(String id);

	String updateProduct(String id, ProductRequestDTO dto);
    
}