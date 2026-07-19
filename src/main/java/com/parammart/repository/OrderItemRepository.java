package com.parammart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parammart.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}