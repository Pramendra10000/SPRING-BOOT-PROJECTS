package com.ps.shop.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ps.shop.entity.UserDetails;

public interface Userdetailsrepo extends JpaRepository<UserDetails, Long>  {
	
	 Optional<UserDetails> findByEmail(String email);

}
