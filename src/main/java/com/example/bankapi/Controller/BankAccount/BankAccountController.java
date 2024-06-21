package com.example.bankapi.Controller.BankAccount;

import com.example.bankapi.DTO.BankAccount.AccountRegistryResponse;
import com.example.bankapi.DTO.BankAccount.AccountRegistryRequest;
import com.example.bankapi.Service.BankAccount.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Tag(name="Bank Account Controller")//gán tên cho swagger
@SecurityRequirement(name="bearerAuth")
public class BankAccountController {
    private final AccountService accountService;
    @PostMapping ("/registry")
    public ResponseEntity<AccountRegistryResponse> accountRegistry(@RequestBody AccountRegistryRequest accountRegistryRequest){
        return ResponseEntity.ok(accountService.registry(accountRegistryRequest));
    }

}
