package com.parammart.controller.secure;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public String dashboard() {
        return "Welcome Employee";
    }

}