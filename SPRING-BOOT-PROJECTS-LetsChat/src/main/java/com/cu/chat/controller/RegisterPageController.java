package com.cu.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cu.chat.service.RegistrationService;

@Controller
public class RegisterPageController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/regtest")
    public String showRegistrationPage() {
        return "Register";  // This corresponds to /WEB-INF/jsp/register.jsp
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("fullName") String fullName,
                               @RequestParam("mobile") String mobile,
                               @RequestParam("city") String city,
                               @RequestParam("email") String email, 
                               @RequestParam("password") String password, 
                               Model model, 
                                RedirectAttributes redirectAttributes) {
        boolean isRegistered = registrationService.registerUser(fullName, mobile, city, email, password);

        if (isRegistered) {
            redirectAttributes.addFlashAttribute("message", "Registration successful! Please login.");
            return "login";
        } else {
            model.addAttribute("error", "Registration failed. Email might already be in use.");
            return "Register";
        }
    }
}
