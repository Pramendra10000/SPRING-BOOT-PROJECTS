package com.ps.shop.serviceImpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ps.shop.dao.productDao;
import com.ps.shop.entity.Product;
import com.ps.shop.repo.ProductRepository;
import com.ps.shop.service.ChicCartService;
import org.springframework.data.domain.Pageable;


@Service
public class ProductServiceimpl implements ChicCartService {

	@Autowired
	private productDao proddao;
	
	@Autowired
	private ProductRepository repo;
	
	
	
	@Override
	public List<Product> getFeaturedProducts() {
		
		
		return proddao.findAll();
	}

	@Override
	 public List<Product> getAllProducts() {
	        return proddao.findAll();
	    }
	
	
	 public List<Product> getProductsSortedByPrice(boolean ascending) {
	        if (ascending) {
	            return repo.findAllByOrderByPriceAsc();
	        } else {
	            return repo.findAllByOrderByPriceDesc();
	        }
	    }
	
	 

	    // Sort by ratings (ascending or descending)
	    public List<Product> getProductsSortedByRatings(boolean ascending) {
	        if (ascending) {
	            return repo.findAllByOrderByRatingsAsc();
	        } else {
	            return repo.findAllByOrderByRatingsDesc();
	        }
	    }

	    
	    // Sort by discount (ascending or descending)
	    public List<Product> getProductsSortedByDiscount(boolean ascending) {
	        if (ascending) {
	            return repo.findAllByOrderByDiscountAsc();
	        } else {
	            return repo.findAllByOrderByDiscountDesc();
	        }
	    }
	
	    // Dynamic sorting method  if we have to do normal sorting like in Aesc way so we can do below.
	    
	    public List<Product> getProductsSorted(String sortBy, boolean ascending) {
	        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	        return repo.findAll(PageRequest.of(0, 10, sort)).getContent();
	    }
//	    
//	    // Shorting with ProductService with Pagination : 
//	    public Page<Product> getProductsWithPagination(int page, int size, String sortBy, boolean ascending) {
//	        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//	        Pageable pageable = PageRequest.of(page, size, sort);
//	        return repo.findAll(pageable);
//	    }
	    
	    
//	    public Page<Product> getProductsWithPagination(int page, int size, String sortBy, boolean ascending) {
//	        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//	        Pageable pageable = PageRequest.of(page, size, sort);
//
//	        return repo.findAll(pageable); // This works now with the correct Pageable
//	    }
	    
	    //Alternet of Above method with if else conditions ..
	    
	 // Method to handle pagination with sorting
//	    public Page<Product> getProductsWithPaginationcondition(int page, int size, String sortBy, boolean ascending) {
//	        // Validate page and size to ensure they are positive
//	        if (page < 0) {
//	            page = 0; // Ensure page is not negative
//	        }
//	        if (size <= 0) {
//	            size = 10; // Default size of 10
//	        }
//
//	        // Sort by the provided field (ascending or descending)
//	        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//
//	        // Create the Pageable object
//	        Pageable pageable = PageRequest.of(page, size, sort);
//
//	        // Return the paginated result
//	        return repo.findAll(pageable);
//	    }

	
}
