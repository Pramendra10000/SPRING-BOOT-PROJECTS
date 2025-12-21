package com.PSNS.Bank.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class TransferRequest {
    private String toAccount;
    private BigDecimal amount;
    private String description;

}
