package com.cu.prods.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cu.prods.entity.Products;
import com.cu.prods.service.Productservice;

@RestController
public class Mycontroller {
	@Autowired
	private Productservice prodservice;
	
//	@GetMapping("/")
//	public String getstrat() {
//		
//		
//		return "Started ";
//	}
//	
//    @GetMapping("/csrf") // we have to use below csrf token and used it for post method 
//	public CsrfToken getCsrfToken(HttpServletRequest req) {
//		
//		return (CsrfToken) req.getAttribute("_csrf");
//		
//	}

	@GetMapping("/home")
	public String home() {
		
		return "This is my app";
		
	}
	
	
	// get All the Products.
	@GetMapping("/products")
	public List<Products> getProducts() {

		return this.prodservice.getProductsservice();
		
	}
	
	// Delete All the Products.
	@DeleteMapping("/deleteall")
	public boolean deleteallproducts() {

		return this.prodservice.deleteallproducts();
		
	}
	// Add  Products .
	@PostMapping("/Addproducts")
	public Products addProducts(@RequestBody Products products) {
		System.out.println("Inside the addProducts"+products);
		return this.prodservice.updatecourse(products);
	}
	
	// Upate the products
	@PutMapping("/updateproducts")
	public Products updateProducts(@RequestBody Products products) {
		System.out.println("Inside the addProducts"+products);
		return this.prodservice.addcourse(products);
	}
	
	// get  Products by its Id.
	@GetMapping("/product/{id}")
	//@RequestMapping(path = "/product/{id}" , method = RequestMethod.GET)
	public Products getproduct(@PathVariable String id) {
		System.out.println("Inside the getproduct"+id);
		return this.prodservice.getproduct(Long.parseLong(id));
		
	}
	
	
	// Delete  Products by its Id.
	@DeleteMapping("/delete/{id}")
	public List<Products> deteteproduct(@PathVariable String id) {
		System.out.println("Inside the deleteproduct"+id);
		return this.prodservice.deleteproduct(Long.parseLong(id));
		
	}
	
	// Delete  Products by its Id.
	@DeleteMapping("/product/{id}")
	public   ResponseEntity<HttpStatus> deleteProducts(@PathVariable String id){
		try {
			System.out.println("Inside the deleteProducts"+id);
			this.prodservice.productdelete(Long.parseLong(id));
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		
		
	}
	
	
	
	
}
