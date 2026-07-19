package com.parammart.dto.response;

import com.parammart.entity.Role;

public record UserResponse(

        Long id,
        String firstName,
        String lastName,
        String email,
        String mobile,
        Role role,
        Boolean enabled

) {}