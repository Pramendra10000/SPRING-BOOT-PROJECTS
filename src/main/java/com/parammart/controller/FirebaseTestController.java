package com.parammart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parammart.serviceTest.FirebaseTestService;


@RestController
@RequestMapping("/api/firebase")
public class FirebaseTestController {

    @Autowired
    private FirebaseTestService service;

    @GetMapping("/test")
    public String test() {
        return service.test();
    }
}
