package com.ps.shop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ps.shop.entity.UserDetails;
import com.ps.shop.model.LoginRequest;
import com.ps.shop.repo.Userdetailsrepo;

@RestController
public class LoginController {
	
	@Autowired
   private Userdetailsrepo userrepo;
	

	@PostMapping("/adduser")
	public ResponseEntity<UserDetails> adduserdetails(@RequestBody UserDetails userdata) {
	    UserDetails Saveuser = null;
	    try {
	        if (!"null".equals(userdata.getEmail()) && !"null".equals(userdata.getPassword()) && !"null".equals(userdata.getFirstName())) {
	            Saveuser = userrepo.save(userdata);
	            return ResponseEntity.ok(Saveuser);
	        } else {
	            System.out.println("Enter Email, Password, and Firstname at least.");
	            return ResponseEntity.badRequest().body(null);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	 @GetMapping("/alluser") 
	 public ResponseEntity<List<UserDetails>> getAllUserDetails() {
		List<UserDetails> allUsers = userrepo.findAll();
		return ResponseEntity.ok(allUsers);
	 }

	 
	 
	 
	
	 @PostMapping("/checkLoginDetails")
	    public ResponseEntity<UserDetails> checkLoginDetails(@RequestBody LoginRequest loginRequest) {
	        try {
	            Optional<UserDetails> userOptional = userrepo.findByEmail(loginRequest.getEmail());
	            if (userOptional.isPresent()) {
	                UserDetails user = userOptional.get();
	                if (user.getPassword().equals(loginRequest.getPassword())) {
	                    return ResponseEntity.ok(user); 
	                } else {
	                    return ResponseEntity.status(401).body(null); 
	                }
	            } else {
	                return ResponseEntity.status(404).body(null); 
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(500).body(null); 
	        }
	    }
	 
 

 
}
