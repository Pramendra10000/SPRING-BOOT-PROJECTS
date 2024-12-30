package com.ps.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ps.shop.entity.Product;
import com.ps.shop.repo.ProductRepository;
import com.ps.shop.service.ChicCartService;

@RestController
public class FilterController {


	@Autowired
	private ChicCartService   Service;
	
	
    // Endpoint to get products sorted by price
    @GetMapping("/sorted/price")
    public List<Product> getProductsByPrice(@RequestParam boolean ascending) {
    	
    	
    	System.out.println("INSIDE IN : : getProductsByPrice : ");
        return Service.getProductsSortedByPrice(ascending);
    }

    // Endpoint to get products sorted by ratings
    @GetMapping("/sorted/ratings")
    public List<Product> getProductsByRatings(@RequestParam boolean ascending) {
        return Service.getProductsSortedByRatings(ascending);
    }

    // Endpoint to get products sorted by discount
    @GetMapping("/sorted/discount")
    public List<Product> getProductsByDiscount(@RequestParam boolean ascending) {
        return Service.getProductsSortedByDiscount(ascending);
    }
    
    @GetMapping("/sorted")
    public List<Product> getProductsSorted(@RequestParam String sortBy, @RequestParam boolean ascending) {
        return Service.getProductsSorted(sortBy, ascending);
    }

    
//    @GetMapping("/sortedWithPage")
//    public Page<Product> getProductsSortedWithPage(@RequestParam String sortBy, 
//                                           @RequestParam boolean ascending, 
//                                           @RequestParam int page, 
//                                           @RequestParam int size) {
//        return Service.getProductsWithPagination(page, size, sortBy, ascending);
//    }
//    
//    
//    @GetMapping("/sortedWithPage2")
//    public Page<Product> getProductsSortedWithPage2( @RequestParam int page,  @RequestParam int size,
//                                        @RequestParam String sortBy, 
//                                        @RequestParam boolean ascending ) {
//        return Service.getProductsWithPaginationcondition( page,  size,  sortBy,  ascending);
//    }

	
}
