package com.example.bankapi.Service.Authentication;

import com.example.bankapi.DTO.Authentication.AuthenticateRequest;
import com.example.bankapi.DTO.Authentication.AuthenticateResponse;
import com.example.bankapi.DTO.Authentication.RegisterRequest;
import com.example.bankapi.DTO.Authentication.RegisterResponse;
import com.example.bankapi.Entity.Authentication.User;

import java.util.Optional;

public interface IAuthenticationService {
    RegisterResponse register(RegisterRequest request);

    AuthenticateResponse authenticate(AuthenticateRequest request);

    void logOut(String jwtToken,String refreshToken);

    Optional<User> findOptionalByEmail(String email);
}
