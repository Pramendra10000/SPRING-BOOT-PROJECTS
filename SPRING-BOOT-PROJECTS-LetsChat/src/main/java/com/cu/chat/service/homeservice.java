package com.cu.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cu.chat.model.userdetails;
import com.cu.chat.repo.Homerepo;

@Service
public class homeservice {

	@Autowired
	private Homerepo homerepo;

	public List<userdetails> getUserDetails(int id) {
		return homerepo.getuserdetails(id);
	}

}
