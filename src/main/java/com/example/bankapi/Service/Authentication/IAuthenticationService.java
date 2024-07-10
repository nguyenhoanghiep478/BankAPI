package com.example.bankapi.Service.Authentication;

import com.example.bankapi.DTO.Authentication.AuthenticateRequest;
import com.example.bankapi.DTO.Authentication.AuthenticateResponse;
import com.example.bankapi.DTO.Authentication.RegisterRequest;
import com.example.bankapi.DTO.Authentication.RegisterResponse;

public interface IAuthenticationService {
    RegisterResponse register(RegisterRequest request);

    AuthenticateResponse authenticate(AuthenticateRequest request);

    void logOut(String jwtToken);
}
