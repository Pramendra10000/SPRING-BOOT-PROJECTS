package com.ps.shop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Unique ID for the product

    private String name;  // Product name
    private String description;  // Product description
    private double price;  // Price of the product
    private int stockQuantity;  // Available stock quantity
    private String category;  // Category of the product
    private String brand;  // Brand of the product

    @Lob  // This annotation marks the field to be stored as a large object (binary data)
    private byte[] image;  // Image as binary data

    private double ratings;  // Average rating out of 5
    private String sku;  // SKU (Stock Keeping Unit)

    private double discount;  // Discount on the product (if any)

    // Getters, setters, and other methods
    public double getPriceAfterDiscount() {
        if (discount > 0) {
            return price * (1 - discount / 100);
        }
        return price;
    }
}


