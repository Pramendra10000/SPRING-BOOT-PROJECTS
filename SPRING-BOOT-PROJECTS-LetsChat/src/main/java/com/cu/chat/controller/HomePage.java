package com.cu.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cu.chat.model.userdetails;
import com.cu.chat.service.homeservice;

@Controller
public class HomePage {
 
	@Autowired
	private homeservice homeService;

	@GetMapping("/hometest")
	public String gethometest() {
		// System.out.println("Home Page : :");
		int id = 1;
		
		homeService.getUserDetails(id);
		return "Home";
	}

	@GetMapping("/logout")
	public String Logout() {
		return "login";
	}

	@PostMapping("/users")
	public String getUserDetails(@RequestParam int id) {
		
		List<userdetails> data = homeService.getUserDetails(id);
		
		return "Home";
	}
}
