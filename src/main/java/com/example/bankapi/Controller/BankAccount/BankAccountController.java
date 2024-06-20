package com.example.bankapi.Controller.BankAccount;

import com.example.bankapi.DTO.BankAccount.AccountRegistryResponse;
import com.example.bankapi.DTO.BankAccount.AccountRegistryRequest;
import com.example.bankapi.Service.BankAccount.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class BankAccountController {
    private final AccountService accountService;
    @PostMapping ("/registry")
    public ResponseEntity<AccountRegistryResponse> accountRegistry(@RequestBody AccountRegistryRequest accountRegistryRequest){
        return ResponseEntity.ok(accountService.registry(accountRegistryRequest));
    }

}
