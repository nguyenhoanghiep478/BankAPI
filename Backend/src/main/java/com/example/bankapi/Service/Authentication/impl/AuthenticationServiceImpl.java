package com.example.bankapi.Service.Authentication.impl;

import com.example.bankapi.Config.GlobalConfig.StaticVar;
import com.example.bankapi.DTO.Authentication.AuthenticateRequest;
import com.example.bankapi.DTO.Authentication.AuthenticateResponse;
import com.example.bankapi.DTO.Authentication.RegisterRequest;
import com.example.bankapi.DTO.Authentication.RegisterResponse;
import com.example.bankapi.DTO.Email.EmailRequest;
import com.example.bankapi.Entity.Authentication.ROLE;
import com.example.bankapi.Entity.Authentication.User;
import com.example.bankapi.Controller.Config.ExceptionHandle.UserEmailExistedException;
import com.example.bankapi.Service.Authentication.IAuthenticationService;
import com.example.bankapi.Service.Authentication.IJWTService;
import com.example.bankapi.Service.Email.impl.EmailService;
import com.example.bankapi.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final com.example.bankapi.Repositories.Authentication.User repository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final IJWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final UserService userService;

    @Transactional
    public RegisterResponse register(RegisterRequest request){
        User user = userService.save(modelMapper.map(request,User.class)).orElse(null);
        return modelMapper.map(user,RegisterResponse.class);
    }
    @Transactional
    public AuthenticateResponse authenticate(AuthenticateRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = repository.findByEmail(request.getEmail()).orElseThrow(()-> new BadCredentialsException("Bad credentials"));
        AuthenticateResponse response = new AuthenticateResponse();
        response.setFullName(user.getFullName());
        response.setPhone(user.getPhone());
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        response.setToken(token);
        response.setRefreshToken(refreshToken);
        return response;
    }
    public void logOut(String token,String refreshToken){
        jwtService.deleteToken(token);
        jwtService.deleteToken(refreshToken);
    }

    @Override
    public Optional<User> findOptionalByEmail(String email) {
        return repository.findByEmail(email);
    }
}
