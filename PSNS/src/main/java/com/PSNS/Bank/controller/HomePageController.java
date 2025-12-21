package com.PSNS.Bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class HomePageController {
	
   

    @GetMapping("/transfer")
    public String transferPage() {
        return "Transfer";
    }

    @GetMapping("/transaction")
    public String transactionsPage() {
        return "Transection";
    }

    @GetMapping("/account")
    public String accountDetailsPage() {
        return "Accountdetails";
    }
}


