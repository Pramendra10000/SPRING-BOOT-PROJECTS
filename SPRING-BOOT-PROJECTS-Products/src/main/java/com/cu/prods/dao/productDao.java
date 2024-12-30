package com.cu.prods.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cu.prods.entity.Products;

public interface productDao extends JpaRepository<Products, Long> {
	
	

	
}
