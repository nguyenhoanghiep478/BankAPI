package com.example.bankapi.Controller.Authentication;

import com.example.bankapi.DTO.Authentication.AuthenticateRequest;
import com.example.bankapi.DTO.Authentication.AuthenticateResponse;
import com.example.bankapi.DTO.Authentication.RegisterRequest;
import com.example.bankapi.DTO.Authentication.RegisterResponse;
import com.example.bankapi.DTO.Email.EmailRequest;
import com.example.bankapi.DTO.Email.VerificationEmailRequest;
import com.example.bankapi.DTO.Email.VerificationEmailResponse;
import com.example.bankapi.Service.Authentication.IAuthenticationService;
import com.example.bankapi.Service.Email.IEmailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
@SecurityRequirement(name="")
@EnableAsync(proxyTargetClass = true)
@Tag(name="Authentication Controller")//gán tên cho swagger
public class AuthenticationController {
    private final IAuthenticationService service;
    private final IEmailService emailService;
    @PostMapping ("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody  @Valid RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticateResponse> authenticate(@RequestBody @Valid AuthenticateRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }
    @PostMapping("/logOut")
    public void logOut(@RequestHeader(name = "Authorization") String token,@RequestHeader(name="RefreshToken") String refreshToken){
        String jwtToken = token.replace("Bearer ","");

        service.logOut(jwtToken,refreshToken);
    }
//    @PostMapping("/send-email")
//    public ResponseEntity<void> sendMail(@RequestBody EmailRequest request){
//        return ResponseEntity.ok(emailService.sendSimpleMail(request));
//    }
    @PostMapping("/verify-email")
    public ResponseEntity<VerificationEmailResponse> verifyEmail(@RequestBody VerificationEmailRequest request){
        return ResponseEntity.ok(emailService.verifyEmail(request));
    }
}
