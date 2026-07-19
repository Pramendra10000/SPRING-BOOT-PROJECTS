package com.parammart.dto.response;

public record LoginResponse(
        String token,
        String role,
        String message
) {}
