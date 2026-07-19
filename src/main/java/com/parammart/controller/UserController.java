package com.parammart.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/me")
    public Authentication me(Authentication authentication) {
    	log.info("Inside App");
        return authentication;
    }

}