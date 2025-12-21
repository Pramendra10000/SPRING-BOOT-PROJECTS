package com.PSNS.Bank.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.PSNS.Bank.model.TransferRequest;

@Controller
public class TransferController {



    @PostMapping("/transfer-money")
    public String processTransfer(
            @RequestParam String recipient,
            @RequestParam Double amount,
            @RequestParam(required = false) String description,
            @RequestParam String type,
            Model model) {

      
        System.out.println("Recipient: " + recipient);
        System.out.println("Amount: " + amount);
        System.out.println("Description: " + description);
        System.out.println("Type: " + type);

        model.addAttribute("success",
                "Money transferred successfully!");

        return "redirect:/transactions";
    }
    
    

    @RestController
    @RequestMapping("/api/transfer")
    public class TransferApiController {

        @PostMapping
        public Map<String, String> transfer(@RequestBody TransferRequest req) {

            // Service call here (DB / validation / balance check)

            return Map.of(
                "status", "SUCCESS",
                "message", "₹" + req.getAmount() + " transferred successfully"
            );
        }
    }

}

