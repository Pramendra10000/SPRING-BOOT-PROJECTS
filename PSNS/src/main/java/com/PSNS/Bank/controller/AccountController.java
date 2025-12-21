package com.PSNS.Bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @GetMapping("/accountdetails2")
    public String accountDetails3() {
        return "Accountdetailss"; // JSP name
    }
}

