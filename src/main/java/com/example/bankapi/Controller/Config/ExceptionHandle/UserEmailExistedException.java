package com.example.bankapi.Controller.Config.ExceptionHandle;

public class UserEmailExistedException extends RuntimeException{
    public UserEmailExistedException(String message){
        super(message);
    }
}
