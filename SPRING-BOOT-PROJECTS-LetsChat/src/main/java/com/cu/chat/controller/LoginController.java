package com.cu.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.cu.chat.service.LoginService;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/logtest")
    public String helloPage() { 
        System.out.println("Login JSP");
        return "login";  // This corresponds to /WEB-INF/jsp/hello.jsp
    }

    @PostMapping("/logwithdata")
    public String login(@RequestParam("email") String email, 
                        @RequestParam("password") String password, 
                        Model model, 
                        RedirectAttributes redirectAttributes) {
        // Authenticate user
        boolean isAuthenticated = loginService.authenticateUser(email, password);

        if (isAuthenticated) {
            // Redirect to a success page
            redirectAttributes.addFlashAttribute("message", "Login successful!");
            return "Home";
        } else {
            // Add error message to model and return to login page
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }
}
