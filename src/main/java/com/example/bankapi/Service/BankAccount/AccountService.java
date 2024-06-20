package com.example.bankapi.Service.BankAccount;

import com.example.bankapi.Config.GlobalConfig.StaticVar;
import com.example.bankapi.DTO.BankAccount.AccountRegistryResponse;
import com.example.bankapi.DTO.BankAccount.AccountRegistryRequest;
import com.example.bankapi.Entity.BankAccount.Account;
import com.example.bankapi.Entity.BankAccount.CheckingAccount;
import com.example.bankapi.Entity.BankAccount.SavingAccount;
import com.example.bankapi.Entity.Receipt.Receipt;
import com.example.bankapi.ExceptionHandle.InsufficientBalanceException;
import com.example.bankapi.ExceptionHandle.InvalidAccountTypeException;
import com.example.bankapi.Repositories.BankAccount.AccountRepository;
import com.example.bankapi.Repositories.User;
import com.example.bankapi.Service.BankAccount.Strategy.AccountStrategy;
import com.example.bankapi.Service.BankAccount.Strategy.impl.CheckingAccountStrategy;
import com.example.bankapi.Service.BankAccount.Strategy.impl.SavingAccountStrategy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
public class AccountService {
    private final Map<Class<? extends Account>, AccountStrategy> strategies = new HashMap<>();
    private final AccountRepository accountRepository;
    @PersistenceContext
    private final EntityManager manager;
    private final User user;
    private final ModelMapper modelMapper;
    @Autowired
    public AccountService(SavingAccountStrategy savingAccount, CheckingAccountStrategy checkingAccount,ModelMapper modelMapper,User user,AccountRepository accountRepository,EntityManager entityManager){
        this.strategies.put(SavingAccount.class,savingAccount);
        this.strategies.put(CheckingAccount.class,checkingAccount);
        this.modelMapper = modelMapper;
        this.user = user;
        this.accountRepository = accountRepository;
        this.manager = entityManager;
    }
    @Transactional
    public AccountRegistryResponse registry(AccountRegistryRequest request){
            long startTime = System.currentTimeMillis();
            Account account = createInforAccount(request);
            AccountStrategy accountStrategy=  strategies.get(account.getClass());
            if(accountStrategy== null){
                throw new IllegalArgumentException("No strategy found for account type "+ account.getAccountType());
            }
            accountStrategy.save(account);
            long endtime = System.currentTimeMillis();
            log.info("Excution time:{} ms",endtime-startTime);
            return modelMapper.map(account, AccountRegistryResponse.class);
    }

    public Boolean updateAmount(String accountNumber,double amount){
        return manager.createQuery("Update Account a Set a.balance = a.balance+ :amount where a.accountNumber=:accountNumber")
                .setParameter("amount", amount)
                .setParameter("accountNumber", accountNumber)
                .executeUpdate() == 1;

    }
    public Boolean isBalanceGreaterThan(double amount,String accountNumber){
        Account account = accountRepository.findAccountByAccountNumber(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account not found for account number: " + accountNumber);
        }
        return account.getBalance() - amount>= 0;
    }
    @Transactional
    public boolean handleBalanceByReceipt(Receipt receipt,String receiveAccountNumber){
        switch (receipt.getReceiptType()) {
            case StaticVar.DEPOSIT_RECEIPT:{
                return updateAmount(receipt.getAccountNumber(), receipt.getAmount());

            }
            case StaticVar.WITHDRAWAL_RECEIPT:{
                if(receipt.getAccount().getAccountType().equals(StaticVar.CHECKING_ACCOUNT)){
                    if (isBalanceGreaterThan(receipt.getAmount(), receipt.getAccountNumber())) {
                        return updateAmount(receipt.getAccountNumber(), receipt.getAmount() * -1);
                    } else {
                        throw new InsufficientBalanceException(StaticVar.IN_SUFFICIENT_EXCEPTION_MESSAGE);
                    }
                }else{
                    throw new InvalidAccountTypeException(StaticVar.INVALID_ACCOUNT_TYPE_EXCEPTION_MESSAGE);
                }
            }
            case StaticVar.TRANSFER_RECEIPT: {
                if(receipt.getAccount().getAccountType().equals(StaticVar.CHECKING_ACCOUNT)){
                    if (isBalanceGreaterThan(receipt.getAmount(), receipt.getAccountNumber())) {
                        if (updateAmount(receiveAccountNumber, receipt.getAmount())) {
                            return updateAmount(receipt.getAccountNumber(), receipt.getAmount() * -1);
                        }
                    } else {
                        throw new InsufficientBalanceException(StaticVar.IN_SUFFICIENT_EXCEPTION_MESSAGE);
                    }
                }else{
                    throw new InvalidAccountTypeException(StaticVar.INVALID_ACCOUNT_TYPE_EXCEPTION_MESSAGE);
                }
            }
            default : {
                throw new IllegalArgumentException("No strategy found for receipt type " + receipt.getReceiptType());
            }
        }
    }

    public Account createInforAccount(AccountRegistryRequest request){
        var user = this.user.findByEmail(request.getUserEmail()).orElseThrow();
        var account = Account.builder()
                .accountType(request.getAccountType())
                .createDate(LocalDateTime.now())
                .balance(StaticVar.INIT_BALANCE)
                .accountNumber(getAccountNumber())
                .user(user)
                .build();
        if(request.getAccountType().equals(StaticVar.SAVING_ACCOUNT)){
            return modelMapper.map(account,SavingAccount.class);
        }
        if(request.getAccountType().equals(StaticVar.CHECKING_ACCOUNT)){
            return modelMapper.map(account,CheckingAccount.class);
        }
        return null;
    }

    private String getAccountNumber() {
        String accountNumber = generateAccountNumber();
        while(accountRepository.existsByAccountNumber(accountNumber)){
            accountNumber = getAccountNumber();
        }
        return accountNumber;
    }
    private String generateAccountNumber(){
        StringBuilder accountNumber = new StringBuilder();
        Random random = new Random();
        for(int i = 0 ; i<StaticVar.ACCOUNT_LENGTH;i++){
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }
    @Transactional(readOnly = true)
    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.findAccountByAccountNumber(accountNumber);
    }
}
