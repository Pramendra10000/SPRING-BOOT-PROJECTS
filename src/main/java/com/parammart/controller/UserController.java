package com.parammart.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parammart.dto.response.CurrentUserResponse;
import com.parammart.entity.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/me")
    public CurrentUserResponse me(Authentication authentication) {

        log.info("Fetching current logged-in user");

        User user = (User) authentication.getPrincipal();

        return new CurrentUserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getMobile(),
                user.getRole(),
                user.getRole().getPermissions(),
                user.getEnabled()
        );
    }
}