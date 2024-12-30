package com.ps.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ps.shop.entity.Product;


public interface productDao extends JpaRepository<Product, Long> {
	
}

