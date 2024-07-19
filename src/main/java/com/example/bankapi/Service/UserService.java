package com.example.bankapi.Service;

import com.example.bankapi.Config.GlobalConfig.StaticVar;
import com.example.bankapi.Controller.Config.ExceptionHandle.UserEmailExistedException;
import com.example.bankapi.DTO.Authentication.RegisterResponse;
import com.example.bankapi.DTO.Email.EmailRequest;
import com.example.bankapi.Entity.Authentication.ROLE;
import com.example.bankapi.Repositories.Authentication.User;
import com.example.bankapi.Service.Email.impl.EmailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final User repository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public com.example.bankapi.Entity.Authentication.User findByEmail(String userEmail) {
        return repository.findByEmail(userEmail).orElseThrow(
                () -> new RuntimeException("User not found")
        );
    }
    public Optional<com.example.bankapi.Entity.Authentication.User> findOptionalByEmail(String userEmail) {
        return repository.findByEmail(userEmail);
    }
    @Transactional
    public Optional<com.example.bankapi.Entity.Authentication.User> save(com.example.bankapi.Entity.Authentication.User user) {
        if(repository.existsByEmail(user.getEmail())){
            if(user.getSource()!= null){
                //update exist user
                return Optional.of(repository.save(user));
            }
            throw new UserEmailExistedException(StaticVar.USER_EMAIL_EXISTED_EXCEPTION_MESSAGE);
        }else{
            if(user.getPassword()!=null){
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            user.setRole(user.getRole() == null ? ROLE.USER : ROLE.ADMIN);
            if(user.getSource()!= null){
                EmailRequest emailRequest = new EmailRequest();
                emailRequest.setRecipient(user.getEmail());
                emailService.sendSimpleMail(emailRequest);
            }
            return Optional.of(repository.save(user));
        }
    }

}
