package com.ps.shop.dto;

import lombok.Data;


@Data
public class ProductDTO2 {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private String category;
    private String brand;
    private String base64Image;  // Base64 Image String
    private double ratings;
    private String sku;
    private double discount;

    // Constructor for DTO
    public ProductDTO2(Long id, String name, String description, double price, int stockQuantity,
                      String category, String brand, String base64Image, double ratings, String sku, double discount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.brand = brand;
        this.base64Image = base64Image;
        this.ratings = ratings;
        this.sku = sku;
        this.discount = discount;
    }
}
