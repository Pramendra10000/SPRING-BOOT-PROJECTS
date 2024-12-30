package com.ps.shop.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Mycontroller {

	@Value("${server.port}")
    private int port;
	
	@GetMapping("/")
	public String MyfirstChicCart() {
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // its optional if you want we directly use +now in below by skipping these lines.$$PS
		System.out.println("ChicCart Application  Running on Port"+port+"In time"+now.format(formatter));
		  return "ChicCart Application Running on Port: " + port + " at time: : " + now;
	}
	
}
