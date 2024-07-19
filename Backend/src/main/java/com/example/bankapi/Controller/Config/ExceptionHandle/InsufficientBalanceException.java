package com.example.bankapi.Controller.Config.ExceptionHandle;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(String message){
        super(message);
    }
}
