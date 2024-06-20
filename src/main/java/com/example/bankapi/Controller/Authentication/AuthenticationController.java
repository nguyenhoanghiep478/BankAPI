package com.example.bankapi.Controller.Authentication;

import com.example.bankapi.DTO.Authentication.AuthenticateRequest;
import com.example.bankapi.DTO.Authentication.AuthenticateResponse;
import com.example.bankapi.DTO.Authentication.RegisterRequest;
import com.example.bankapi.DTO.Authentication.RegisterResponse;
import com.example.bankapi.Service.Authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService service;

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
    @GetMapping("/test")
    public String test(){
        return "hello";
    }

}
