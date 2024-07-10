package com.example.bankapi.Service;

import com.example.bankapi.Repositories.Authentication.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final User repository;
    private final ModelMapper modelMapper;

}
