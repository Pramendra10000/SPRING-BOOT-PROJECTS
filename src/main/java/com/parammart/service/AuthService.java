package com.parammart.service;

import com.parammart.dto.request.LoginRequest;
import com.parammart.dto.request.RegisterRequest;
import com.parammart.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}