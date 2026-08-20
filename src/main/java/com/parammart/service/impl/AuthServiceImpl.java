package com.parammart.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.parammart.dto.request.LoginRequest;
import com.parammart.dto.request.RegisterRequest;
import com.parammart.dto.response.AuthResponse;
import com.parammart.entity.Role;
import com.parammart.entity.User;
import com.parammart.exception.ResourceAlreadyExistsException;
import com.parammart.repository.UserRepository;
import com.parammart.security.JwtService;
import com.parammart.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    // =========================================================
    // REGISTER
    // =========================================================

    @Override
    public AuthResponse register(RegisterRequest request) {

    	if (repository.existsByEmail(request.email())) {
    	    throw new ResourceAlreadyExistsException(
    	            "Email already exists"
    	    );
    	}

    	if (repository.existsByMobile(request.mobile())) {
    	    throw new ResourceAlreadyExistsException(
    	            "Mobile already exists"
    	    );
    	}


        // Create new user
        User user = User.builder()

                .firstName(request.firstName())

                .lastName(request.lastName())

                .email(request.email())

                .mobile(request.mobile())

                .password(
                        passwordEncoder.encode(
                                request.password()
                        )
                )

                // Public registration always creates CUSTOMER
                .role(Role.CUSTOMER)

                .enabled(true)

                .build();


        // Save user
        repository.save(user);


        // Generate JWT
        String token = jwtService.generateToken(user);


        // Return response
        return new AuthResponse(
                token,
                "Registration Successful"
        );
    }


    // =========================================================
    // LOGIN
    // =========================================================

    @Override
    public AuthResponse login(LoginRequest request) {

        // Authenticate email + password
        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );


        // Find authenticated user
        User user = repository
                .findByEmail(request.email())
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );


        // Generate JWT
        String token = jwtService.generateToken(user);


        // Return response
        return new AuthResponse(
                token,
                "Login Successful"
        );
    }
}