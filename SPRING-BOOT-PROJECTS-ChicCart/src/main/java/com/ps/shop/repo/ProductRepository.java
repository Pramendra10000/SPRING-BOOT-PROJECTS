package com.ps.shop.repo;

import com.ps.shop.entity.Product;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Sorting products by price (ascending)
    List<Product> findAllByOrderByPriceAsc();

    // Sorting products by price (descending)
    List<Product> findAllByOrderByPriceDesc();

    // Sorting products by ratings (ascending)
    List<Product> findAllByOrderByRatingsAsc();

    // Sorting products by ratings (descending)
    List<Product> findAllByOrderByRatingsDesc();

    // Sorting products by discount (ascending)
    List<Product> findAllByOrderByDiscountAsc();

    // Sorting products by discount (descending)
    List<Product> findAllByOrderByDiscountDesc();
  
    
    // Correct method signature for pagination and sorting
  //  Page<Product> findAll(Pageable pageable);
}
