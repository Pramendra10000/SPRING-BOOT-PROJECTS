package com.ps.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ps.shop.dto.ProductDTO;
import com.ps.shop.dto.ProductDTO2;
import com.ps.shop.entity.Product;
import com.ps.shop.repo.ProductRepository;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class HomePageController {

    @Autowired
    private ProductRepository productRepository;
    
    
    // Get all products with image as Base64 string
    
    
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        // Fetch all products from the repository
        List<Product> products = productRepository.findAll();

        // Convert products to DTOs (Data Transfer Objects) with Base64-encoded images
        List<ProductDTO> productDTOs = products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        System.out.println("Product info : : "+productDTOs);

        return ResponseEntity.ok(productDTOs);
    }

    // Find a product by its ID
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id) {
        // Fetch product by id using the repository
        Optional<Product> product = productRepository.findById(id);
        
        // If product is found, return it with 200 OK
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            // If product is not found, return 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }
    
    // Add all products with image as Base64 string
    
    @PostMapping("/addproducts")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        try {
            // If image is provided as Base64, decode and set it as byte array
            if (product.getImage() != null && product.getImage().length > 0) {
            	System.out.println("inside If conditions");
                // The image should be a byte[] already, so we directly use it
            } else {
                // If no image provided, you could handle this differently or set a default
            }

            // Save the product to the database
            Product savedProduct = productRepository.save(product);
            
            // Return the saved product
            return ResponseEntity.ok(savedProduct); // Return the saved product with status 200 OK
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Return 400 Bad Request if there's an error
        }
    }
    
   
    // Delete a product by its ID
    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            // Check if the product exists in the database
            if (!productRepository.existsById(id)) {
                return ResponseEntity.notFound().build(); // Return 404 if product not found
            }
            
            // Delete the product by its ID
            productRepository.deleteById(id);

            // Return a successful response with no content
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Return 500 Internal Server Error if an exception occurs
        }
    }
    
    // Update a product by its ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        // Fetch product by id from the repository
        Optional<Product> existingProductOpt = productRepository.findById(id);

        if (!existingProductOpt.isPresent()) {
            // If product is not found, return 404 Not Found
            return ResponseEntity.notFound().build();
        }

        // Get the existing product
        Product existingProduct = existingProductOpt.get();

        // Update the existing product fields with the new data
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStockQuantity(updatedProduct.getStockQuantity());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setBrand(updatedProduct.getBrand());
        existingProduct.setImage(updatedProduct.getImage());
        existingProduct.setRatings(updatedProduct.getRatings());
        existingProduct.setSku(updatedProduct.getSku());
        existingProduct.setDiscount(updatedProduct.getDiscount());

        // Save the updated product in the database
        Product savedProduct = productRepository.save(existingProduct);

        // Return the updated product
        return ResponseEntity.ok(savedProduct);
    }

    // Convert Product entity to DTO with Base64 image
    private ProductDTO convertToDTO(Product product) {
    	 String base64Image = product.getImage() != null ? 
                 Base64.getEncoder().encodeToString(product.getImage()) : null;

return new ProductDTO(
    product.getId(),
    product.getName(),
    product.getDescription(),
    product.getPrice(),
    product.getStockQuantity(),
    product.getCategory(),
    product.getBrand(),
    base64Image,  // Base64 image string
    product.getRatings(),
    product.getSku(),
    product.getDiscount()
);
    }
    



@GetMapping("/products2")
public ResponseEntity<List<ProductDTO2>> getAllProducts2() {
    // Fetch all products from the repository
    List<Product> products = productRepository.findAll();

    // Convert products to DTOs (Data Transfer Objects) with Base64-encoded images
    List<ProductDTO2> productDTOs2 = products.stream()
            .map(this::convertToDTO2)
            .collect(Collectors.toList());
    		
    		
    System.out.println("Product info : : " + productDTOs2);

    return ResponseEntity.ok(productDTOs2);
}


//Utility method to convert Product to ProductDTO and encode image to Base64
private ProductDTO2 convertToDTO2(Product product) {
 // Convert byte[] image to Base64 string
 String base64Image = product.getImage() != null ? 
                      Base64.getEncoder().encodeToString(product.getImage()) : null;

 return new ProductDTO2(
         product.getId(),
         product.getName(),
         product.getDescription(),
         product.getPrice(),
         product.getStockQuantity(),
         product.getCategory(),
         product.getBrand(),
         base64Image,  // Base64 image string
         product.getRatings(),
         product.getSku(),
         product.getDiscount()
 );
} 

}


