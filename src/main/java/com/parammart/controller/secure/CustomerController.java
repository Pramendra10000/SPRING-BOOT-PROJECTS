package com.parammart.controller.secure;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String dashboard() {
        return "Welcome Customer";
    }

    @GetMapping("/products")
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public String products() {
        return "Customer Product List";
    }

}