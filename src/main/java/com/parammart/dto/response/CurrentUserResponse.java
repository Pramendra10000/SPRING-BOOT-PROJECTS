package com.parammart.dto.response;

import java.util.Set;

import com.parammart.entity.Permission;
import com.parammart.entity.Role;

public record CurrentUserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String mobile,
        Role role,
        Set<Permission> permissions,
        Boolean enabled
) {
}