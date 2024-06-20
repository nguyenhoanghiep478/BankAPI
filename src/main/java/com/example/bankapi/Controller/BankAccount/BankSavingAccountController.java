package com.example.bankapi.Controller.BankAccount;

import com.example.bankapi.Entity.BankAccount.Account;
import com.example.bankapi.Service.BankAccount.Strategy.impl.SavingAccountStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class BankSavingAccountController {
    private final SavingAccountStrategy accountService;
    @GetMapping("/get-interest-today")
    public ResponseEntity<Double> getInterestToday(@RequestBody Account account){
        return ResponseEntity.ok(accountService.getDividendToday(account.getAccountNumber()));
    }
    @GetMapping("/get-total-dividend")
    public ResponseEntity<Double> getTotalInterest(@RequestBody Account account){
        return ResponseEntity.ok(accountService.getTotalDividend(account.getAccountNumber()));
    }
}
