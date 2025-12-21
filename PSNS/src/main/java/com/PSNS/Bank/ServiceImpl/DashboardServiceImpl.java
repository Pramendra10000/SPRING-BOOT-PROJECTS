package com.PSNS.Bank.ServiceImpl;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl {

    // STATIC DATA (Later → DB / Procedure)
    public Map<String, Object> getDashboardData() {

        return Map.of(
            "userName", "Pramendra singh",
            "totalBalance", new BigDecimal("45823.50"),
            "accountNumber", "**** **** **** 4782",
            "transactions", List.of(
                Map.of("title", "Salary Credit", "date", "2024-12-15", "amount", "+5000", "type", "plus"),
                Map.of("title", "Grocery Store", "date", "2024-12-14", "amount", "-150", "type", "minus"),
                Map.of("title", "Rent Payment", "date", "2024-12-13", "amount", "-1200", "type", "minus")
            )
        );
    }
}
