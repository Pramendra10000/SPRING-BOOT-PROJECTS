package com.ps.shop.model;

import java.util.List;

import com.ps.shop.entity.Category;
import com.ps.shop.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HomepageResponse {
    private List<Product> featuredProducts;
    private List<Category> categories;
}
