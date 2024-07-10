package com.example.bankapi.Controller.Authentication;

import com.example.bankapi.DTO.Authentication.AuthenticateRequest;
import com.example.bankapi.DTO.Authentication.AuthenticateResponse;
import com.example.bankapi.DTO.Authentication.RegisterRequest;
import com.example.bankapi.DTO.Authentication.RegisterResponse;
import com.example.bankapi.DTO.Email.EmailRequest;
import com.example.bankapi.DTO.Email.VerificationEmailRequest;
<<<<<<< HEAD
import com.example.bankapi.Service.Authentication.IAuthenticationService;
import com.example.bankapi.Service.Authentication.impl.AuthenticationServiceImpl;
=======
import com.example.bankapi.Service.Authentication.AuthenticationService;
>>>>>>> 8bf2d517198aeb5b5c93bc71d0821bb6b2eddbb3
import com.example.bankapi.Service.Email.IEmailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
@SecurityRequirement(name="")
@Tag(name="Authentication Controller")//gán tên cho swagger
public class AuthenticationController {
<<<<<<< HEAD
    private final IAuthenticationService service;
=======
    private final AuthenticationService service;
>>>>>>> 8bf2d517198aeb5b5c93bc71d0821bb6b2eddbb3
    private final IEmailService emailService;
    @PostMapping ("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticateResponse> authenticate(@RequestBody AuthenticateRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }
    @PostMapping("/logOut")
    public void logOut(@RequestHeader(name = "Authorization") String token){
        String jwtToken = token.replace("Bearer ","");
        service.logOut(jwtToken);
    }
    @PostMapping("/send-email")
    public ResponseEntity<String> sendMail(@RequestBody EmailRequest request){
        return ResponseEntity.ok(emailService.sendSimpleMail(request));
    }
    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestBody VerificationEmailRequest request){
        return ResponseEntity.ok(emailService.verifyEmail(request));
    }
}
