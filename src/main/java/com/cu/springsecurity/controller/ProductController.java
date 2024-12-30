package com.cu.springsecurity.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cu.springsecurity.model.Products;

@RestController
public class ProductController {
	
	
	List<Products> prod = new ArrayList<>(List.of(new Products(1,"test1","test1"),new Products(1,"test1","test1")));
	

	@GetMapping("/prod")
	public List<Products> getalllproduct() {
		
		return prod;
	}
	
	@PostMapping("/prod")
	public List<Products> addproducts(@RequestBody Products products) {
		prod.add(products);
		
		return prod;
		
	}
	
	
	
}
