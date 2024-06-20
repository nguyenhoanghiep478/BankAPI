package com.example.bankapi.Entity.BankAccount;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class SavingAccount extends Account {
    private double totalDividend;
    private double dividendToday;
    private double principalAmount;
    private LocalDateTime lastInterestDate;
}
