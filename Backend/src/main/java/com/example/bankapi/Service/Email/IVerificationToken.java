package com.example.bankapi.Service.Email;

import com.example.bankapi.Entity.Authentication.User;

public interface IVerificationToken {
    public String validateVerificationToken(String token);
    public String generateVerificationToken(User user);
}
