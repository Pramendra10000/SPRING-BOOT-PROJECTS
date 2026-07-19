package com.parammart.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.parammart.dto.request.LoginRequest;
import com.parammart.dto.request.RegisterRequest;
import com.parammart.dto.response.AuthResponse;
import com.parammart.entity.User;
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

    @Override
    public AuthResponse register(RegisterRequest request) {

        if(repository.existsByEmail(request.email()))
            throw new RuntimeException("Email already exists");

        if(repository.existsByMobile(request.mobile()))
            throw new RuntimeException("Mobile already exists");

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .mobile(request.mobile())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .enabled(true)
                .build();

        repository.save(user);

        String token = jwtService.generateToken(user);

        return new AuthResponse(token,"Registration Successful");
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()));

        User user = repository.findByEmail(request.email()).orElseThrow();

        String token = jwtService.generateToken(user);

        return new AuthResponse(token,"Login Successful");
    }
}