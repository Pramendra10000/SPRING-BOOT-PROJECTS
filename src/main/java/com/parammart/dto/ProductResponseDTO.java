package com.parammart.dto;


import lombok.Data;

@Data
public class ProductResponseDTO {

    private String id;
    private String name;
    private String brand;
    private String category;
    private double price;
    private int stock;
}
