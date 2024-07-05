package com.example.bankapi.Service.Email.impl;

import com.example.bankapi.Config.GlobalConfig.StaticVar;
import com.example.bankapi.Entity.Authentication.UserVerificationToken;
import com.example.bankapi.Repositories.Authentication.User;
import com.example.bankapi.Repositories.Authentication.UserVerificationRepository;
import com.example.bankapi.Service.Email.IVerificationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationTokenService implements IVerificationToken {
    private final UserVerificationRepository repository;
    private final User userRepository;

    @Override
    public String validateVerificationToken(String token) {
        token=token.replaceAll("//s","");
        Optional<UserVerificationToken> verificationToken = repository.findByToken(token);
        if(verificationToken.isEmpty()){
            return "Invalid Token";
        }
        UserVerificationToken userVerificationToken = verificationToken.get();
        if(userVerificationToken.getExpiryDate().isBefore(LocalDateTime.now())){
            return "Token Expired";
        }
        com.example.bankapi.Entity.Authentication.User user = userVerificationToken.getUser();
        user.setIsVerified(true);
        userRepository.save(user);
        return "Your Email verified successful";
    }

    @Override
    public String generateVerificationToken(com.example.bankapi.Entity.Authentication.User user) {
        SecureRandom random = new SecureRandom();
        int token = getLengthToken(StaticVar.VERIFICATION_TOKEN_LENGTH) + random.nextInt(getLengthToken(StaticVar.VERIFICATION_TOKEN_LENGTH)*9);
        UserVerificationToken verifyToken = new UserVerificationToken();
        verifyToken.setToken(Integer.toString(token));
        verifyToken.setUser(user);
        int length = verifyToken.getToken().length();
        repository.save(verifyToken);
        return Integer.toString(token);
    }
    private int getLengthToken(int length){
        int currenLength=1;
        for(int i =1;i<length;i++){
            currenLength*=10;
        }
        return currenLength;
    }
}
