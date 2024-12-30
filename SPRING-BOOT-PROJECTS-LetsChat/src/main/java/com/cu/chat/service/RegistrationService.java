package com.cu.chat.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cu.chat.model.User;
import com.cu.chat.repo.UserRepository;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    public boolean registerUser(String fullName, String mobile, String city, String email, String password) {
        // Check if email already exists
        if (userRepository.findByEmail(email) != null) {
            return false;
        }

        // Save user to database
        User user = new User();
        user.setFullName(fullName);
        user.setMobile(mobile);
        user.setCity(city);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        return true;
    }
}

