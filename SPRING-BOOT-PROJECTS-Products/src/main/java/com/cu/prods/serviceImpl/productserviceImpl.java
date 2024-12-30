package com.cu.prods.serviceImpl;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cu.prods.dao.productDao;
import com.cu.prods.entity.Products;
import com.cu.prods.service.Productservice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class productserviceImpl implements Productservice {

	@Autowired
	private productDao proddao;

	List<Products> list;

//	public productserviceImpl() {
//		list = new ArrayList<>();
//		list.add(new Products(1, "Laptop", "High-performance laptop with 16GB RAM and 512GB SSD"));
//		list.add(new Products(2, "Smartphone", "Latest model with 5G support and 128GB storage"));
//		list.add(new Products(3, "Headphones", "Noise-canceling over-ear headphones with deep bass"));
//		list.add(new Products(4, "Watch", "Smartwatch with heart rate monitoring and fitness tracking"));
//		list.add(new Products(5, "Bluetooth Speaker", "Portable Bluetooth speaker with 12 hours of battery life"));
//		list.add(new Products(6, "Keyboard", "Mechanical keyboard with RGB backlighting"));
//		list.add(new Products(7, "Mouse", "Wireless ergonomic mouse with precision tracking"));
//		list.add(new Products(8, "Tablet", "10-inch tablet with HD display and long battery life"));
//		list.add(new Products(9, "Camera", "DSLR camera with 24MP sensor and 4K video recording"));
//		list.add(new Products(10, "Smart TV", "55-inch 4K Smart TV with built-in streaming apps"));
//
//	}

	@Override
	public List<Products> getProductsservice() {
		return proddao.findAll();
	}

	// For removing all Product from list
	@Override
	public boolean deleteallproducts() {
		list.clear(); // Clears the list
		return list.isEmpty(); // Returns true if the list is empty after clearing
	}


	
	@Override
	public Products getproduct(long id) {
	    Products product = proddao.getReferenceById(id);
	    // Initialize the lazy-loaded properties
	    Hibernate.initialize(product);
	    return product;
	}


//	@Transactional 
//	@Override 
//	public Products getproduct(long id) {
//		return proddao.getReferenceById(id);
//	}

    // For removing Product as per id from list
	@Override
	public List<Products> deleteproduct(long id) {
		Products p = null;

		// Iterate through the list and find the product to remove
		for (Products product : list) {
			if (product.getId() == id) {
				p = product; // Found the product
				list.remove(product); // Remove the product from the list
				break; // Exit loop once the product is removed
			}
		}

		// Return the remaining products (updated list) after deletion
		return list;
	}

	@Override
	public Products addcourse(Products products) {
		// TODO Auto-generated method stub
		proddao.save(products);

		return products;
	}

	// For updating the Product

	@Override
	public Products updatecourse(Products products) {

		proddao.save(products);

		return products;
	}

	// For Deleting the Product

	@Override
	public void productdelete(long long1) {
		Products entity = proddao.getReferenceById(long1);
		proddao.delete(entity);
	}

}
