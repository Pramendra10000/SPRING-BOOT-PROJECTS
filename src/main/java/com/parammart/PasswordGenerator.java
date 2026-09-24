package com.parammart;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        String newPassword = "Test@123";

        String hash = encoder.encode(newPassword);

        System.out.println("New password: " + newPassword);
        System.out.println("BCrypt hash:");
        System.out.println(hash);
    }
}