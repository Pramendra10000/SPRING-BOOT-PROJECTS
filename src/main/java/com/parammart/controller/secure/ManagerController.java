package com.parammart.controller.secure;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('MANAGER')")
    public String dashboard() {
        return "Welcome Manager";
    }

    @GetMapping("/products")
    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
    public String products() {
        return "Manager Product Screen";
    }

}