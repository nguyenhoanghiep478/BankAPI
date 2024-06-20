package com.example.bankapi.Controller.AdminController;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/home")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String homePage(){
        return "hello admin";

    }
}
