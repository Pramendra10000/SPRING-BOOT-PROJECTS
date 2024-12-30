package com.cu.chat.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cu.chat.model.User;
import com.cu.chat.repo.UserRepository;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}

