package com.cu.prods.service;

import java.util.List;

import com.cu.prods.entity.Products;

public interface Productservice {

	public List<Products> getProductsservice();
	
	public Products  getproduct(long id);

	public boolean deleteallproducts();

	public List<Products> deleteproduct(long id);

	public Products addcourse(Products products);

	public Products updatecourse(Products products);

	public void productdelete(long long1);

	
	
}
