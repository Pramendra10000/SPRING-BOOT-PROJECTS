package com.PSNS.Bank.controller;

import org.springframework.stereotype.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.PSNS.Bank.Service.ProfileService;
import com.PSNS.Bank.model.ProfileDto;

import org.springframework.web.bind.annotation.ModelAttribute;


import jakarta.servlet.http.HttpSession;



@Controller
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    // ===================== PROFILE PAGE =====================
    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {

        @SuppressWarnings("unchecked")
        Map<String, Object> user =
                (Map<String, Object>) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        ProfileDto profile = new ProfileDto();
        profile.setUserName((String) user.get("username"));
        profile.setFirstName((String) user.get("firstName"));
        profile.setLastName((String) user.get("lastName"));
        profile.setEmail((String) user.get("email"));
        profile.setPhone((String) user.get("phone"));
        profile.setAddress((String) user.get("address"));

        model.addAttribute("profile", profile);
        return "profile";
    }

    // ===================== UPDATE PROFILE =====================
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute ProfileDto profileDto,
                                HttpSession session) {

        @SuppressWarnings("unchecked")
        Map<String, Object> user =
                (Map<String, Object>) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        // 🔐 Trust username only from session
        profileDto.setUserName((String) user.get("username"));

        // ✅ Save to DB / service
        profileService.updateProfile(profileDto);

        // ✅ DO NOT touch immutable session map
        return "redirect:/profile?success";
    }

}


