package com.example.bankapi.Service;

<<<<<<< HEAD
import com.example.bankapi.Repositories.Authentication.User;
=======
import com.example.bankapi.Repositories.User;
>>>>>>> 8bf2d517198aeb5b5c93bc71d0821bb6b2eddbb3
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final User repository;
    private final ModelMapper modelMapper;

<<<<<<< HEAD
    public com.example.bankapi.Entity.Authentication.User findByEmail(String userEmail) {
        return repository.findByEmail(userEmail);
    }
=======
>>>>>>> 8bf2d517198aeb5b5c93bc71d0821bb6b2eddbb3
}
