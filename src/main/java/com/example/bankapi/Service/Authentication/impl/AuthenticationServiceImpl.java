package com.example.bankapi.Service.Authentication.impl;

import com.example.bankapi.Config.GlobalConfig.StaticVar;
import com.example.bankapi.DTO.Authentication.AuthenticateRequest;
import com.example.bankapi.DTO.Authentication.AuthenticateResponse;
import com.example.bankapi.DTO.Authentication.RegisterRequest;
import com.example.bankapi.DTO.Authentication.RegisterResponse;
import com.example.bankapi.Entity.Authentication.ROLE;
import com.example.bankapi.Entity.Authentication.User;
import com.example.bankapi.Controller.Config.ExceptionHandle.UserEmailExistedException;
import com.example.bankapi.Service.Authentication.IAuthenticationService;
import com.example.bankapi.Service.Authentication.IJWTService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final com.example.bankapi.Repositories.Authentication.User repository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final IJWTService jwtService;
    private final AuthenticationManager authenticationManager;
    @Transactional
    public RegisterResponse register(RegisterRequest request){
        if(!repository.existsByEmail(request.getEmail())){
            var user = modelMapper.map(request, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(request.getRole() == null ? ROLE.USER : ROLE.ADMIN);
            repository.save(user);
            return modelMapper.map(user,RegisterResponse.class);
        }
        throw new UserEmailExistedException(StaticVar.USER_EMAIL_EXISTED_EXCEPTION_MESSAGE);
    }
    @Transactional
    public AuthenticateResponse authenticate(AuthenticateRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail());
        AuthenticateResponse response = modelMapper.map(user, AuthenticateResponse.class);
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        response.setToken(token);
        response.setRefreshToken(refreshToken);
        return response;
    }
    public void logOut(String token){
        jwtService.deleteToken(token);
    }
}
