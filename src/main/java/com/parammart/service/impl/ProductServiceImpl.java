package com.parammart.service.impl;

import com.google.cloud.firestore.*;
import com.parammart.dto.ProductRequestDTO;
import com.parammart.dto.ProductResponseDTO;
import com.parammart.entity.Product;
import com.parammart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private Firestore firestore;

    private static final String COLLECTION = "products";

    // =========================
    // ADD PRODUCT
    // =========================
    @Override
    public String addProduct(ProductRequestDTO dto) {

        String id = UUID.randomUUID().toString();

        Product product = new Product(
                id,
                dto.getName(),
                dto.getBrand(),
                dto.getCategory(),
                dto.getPrice(),
                dto.getStock()
        );

        firestore.collection(COLLECTION)
                .document(id)
                .set(product);

        return "Product added successfully with ID: " + id;
    }

    // =========================
    // GET ALL PRODUCTS
    // =========================
    @Override
    public List<ProductResponseDTO> getAllProducts() {

        try {
            QuerySnapshot snapshot =
                    firestore.collection(COLLECTION).get().get();

            List<ProductResponseDTO> list = new ArrayList<>();

            for (QueryDocumentSnapshot doc : snapshot.getDocuments()) {

                Product product = doc.toObject(Product.class);

                list.add(mapToResponse(doc.getId(), product));
            }
            
            System.out.println("Firestore instance: " +firestore);

            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // =========================
    // GET PRODUCT BY ID
    // =========================
    @Override
    public ProductResponseDTO getProductById(String id) {

        try {
            DocumentSnapshot doc =
                    firestore.collection(COLLECTION)
                            .document(id)
                            .get()
                            .get();

            if (!doc.exists()) {
                return null;
            }

            Product product = doc.toObject(Product.class);

            return mapToResponse(id, product);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // =========================
    // UPDATE PRODUCT
    // =========================
    @Override
    public String updateProduct(String id, ProductRequestDTO dto) {

        Product product = new Product(
                id,
                dto.getName(),
                dto.getBrand(),
                dto.getCategory(),
                dto.getPrice(),
                dto.getStock()
        );

        firestore.collection(COLLECTION)
                .document(id)
                .set(product);

        return "Product updated successfully";
    }

    // =========================
    // DELETE PRODUCT
    // =========================
    @Override
    public String deleteProduct(String id) {

        firestore.collection(COLLECTION)
                .document(id)
                .delete();

        return "Product deleted successfully";
    }

    // =========================
    // MAPPER (ENTITY → DTO)
    // =========================
    private ProductResponseDTO mapToResponse(String id, Product p) {

        ProductResponseDTO dto = new ProductResponseDTO();

        dto.setId(id);
        dto.setName(p.getName());
        dto.setBrand(p.getBrand());
        dto.setCategory(p.getCategory());
        dto.setPrice(p.getPrice());
        dto.setStock(p.getStock());

        return dto;
    }

	@Override
	public String updateProduct(String id, Product product) {
		// TODO Auto-generated method stub
		return null;
	}
}