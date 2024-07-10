package com.example.bankapi.Controller.Config.ExceptionHandle;

public class InvalidAccountTypeException extends RuntimeException{
    public InvalidAccountTypeException(String message){
        super(message);
    }
}
