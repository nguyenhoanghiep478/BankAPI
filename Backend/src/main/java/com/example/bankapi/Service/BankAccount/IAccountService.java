package com.example.bankapi.Service.BankAccount;

import com.example.bankapi.DTO.BankAccount.*;
import com.example.bankapi.Entity.BankAccount.Account;
import com.example.bankapi.Entity.Receipt.Receipt;

import java.util.List;

public interface IAccountService {
    String getAccountNumber();
    boolean handleBalanceByReceipt(Receipt receipt, String receiveAccountNumber);
    AccountRegistryResponse registry(AccountRegistryRequest accountRegistryRequest);
    Account findByAccountNumber(String accountNumber);
    GetBankAccountsResponse findCheckingAccountByEmail(String email);
    GetBankAccountsResponse findAccountByEmail(String email);
    List<Account> getAllAccounts();
    getAccountByAccountNumberResponse findByAccountNumber(getAccountByAccountNumberRequest request);
}
