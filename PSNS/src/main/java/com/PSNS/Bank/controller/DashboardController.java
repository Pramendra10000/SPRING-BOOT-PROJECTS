package com.PSNS.Bank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import com.PSNS.Bank.ServiceImpl.DashboardServiceImpl;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class DashboardController {

    @Autowired
    private DashboardServiceImpl dashboardService;
    
    
    @GetMapping("/dashboard")
    public String dashboardPage(HttpSession session, Model model) {

        Object user = session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("dashboard", dashboardService.getDashboardData());

        return "Dashboard";
    }


}

