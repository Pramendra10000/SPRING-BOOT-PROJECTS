package com.ps.shop.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ps.shop.entity.Product;

public interface ChicCartService {
	
	public List<Product> getFeaturedProducts();
	
   // public List<Category> getAllCategories();

	public List<Product> getAllProducts();

	public List<Product> getProductsSortedByPrice(boolean ascending);

	public List<Product> getProductsSortedByRatings(boolean ascending);

	public List<Product> getProductsSortedByDiscount(boolean ascending);

	public List<Product> getProductsSorted(String sortBy, boolean ascending);

//	public Page<Product> getProductsWithPagination(int page, int size, String sortBy, boolean ascending);
//
//	public Page<Product> getProductsWithPaginationcondition(int page, int size, String sortBy, boolean ascending);
//	
//	

	
}
