package com.cu.springsecurity.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class Homecontroller {

	@GetMapping("/")
	public String getstrat() {
		
		return "Started Project : : :";
		
	}
	
	@GetMapping("/csrf")
	public CsrfToken getCsrfToken(HttpServletRequest req) {
		
		return (CsrfToken) req.getAttribute("_csrf");
	}
	
}
