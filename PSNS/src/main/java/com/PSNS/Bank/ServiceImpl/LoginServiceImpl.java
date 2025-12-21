package com.PSNS.Bank.ServiceImpl;


import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl {

    // STATIC LOGIN (Later → DB / Stored Procedure)
    public Map<String, Object> authenticate(String username, String password) {

        // Temporary hardcoded user
        if ("admin".equals(username) && "admin123".equals(password)) {

            return Map.of(
                "userId", 101,
                "userName", "John Doe",
                "role", "CUSTOMER"
            );
        }else if("user".equals(username) && "user123".equals(password)) {
           return Map.of("userId",102,
        		   "username","PRAMENDRA SINGH",
        		   "role","USER");       	
        }

        return null; // Invalid login
    }
}
