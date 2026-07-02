package com.parammart.dto;

import lombok.Data;

@Data
public class ProductRequestDTO {

    private String name;
    private String brand;
    private String category;
    private double price;
    private int stock;
}