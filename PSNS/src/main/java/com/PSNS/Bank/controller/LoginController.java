package com.PSNS.Bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.PSNS.Bank.ServiceImpl.LoginServiceImpl;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private LoginServiceImpl loginService;

    // Show Login Page
    @GetMapping("/")
    public String loginPage() {
        return "Login"; // Login.jsp
    }

    // Handle Login
    @PostMapping("/login")
    public String doLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        var user = loginService.authenticate(username, password);

        if (user == null) {
            model.addAttribute("error", "Invalid username or password");
            return "Login";
        }

        // Store user in session
        session.setAttribute("loggedInUser", user);

        return "redirect:/dashboard";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}


