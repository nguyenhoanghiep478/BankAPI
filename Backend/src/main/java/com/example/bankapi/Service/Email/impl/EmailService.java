package com.example.bankapi.Service.Email.impl;

import com.example.bankapi.Config.GlobalConfig.StaticVar;
import com.example.bankapi.DTO.Email.EmailRequest;
import com.example.bankapi.DTO.Email.VerificationEmailRequest;
import com.example.bankapi.DTO.Email.VerificationEmailResponse;
import com.example.bankapi.Repositories.Authentication.User;
import com.example.bankapi.Service.Email.IEmailService;
import com.example.bankapi.Service.Email.IVerificationToken;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {
    private final JavaMailSender javaMailSender;
    private final IVerificationToken verificationTokenService;
    private final User userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Async
    public void sendSimpleMail(EmailRequest request) {
        if(request.getAttachment()!=null){
            sendMailWithAttachment(request);
        }
       try{
           String token =verificationTokenService.generateVerificationToken(userRepository.findByEmail(request.getRecipient()).orElseThrow(()-> new UsernameNotFoundException("User not found")));
           SimpleMailMessage mailMessage = new SimpleMailMessage();
           mailMessage.setFrom(StaticVar.MAIL_SENDER);
           mailMessage.setTo(request.getRecipient());
           mailMessage.setSubject(StaticVar.MAIL_SUBJECT);
           mailMessage.setText(StaticVar.MAIL_TEXT+token);
           javaMailSender.send(mailMessage);

        }catch (Exception e){


       }
    }

    @Override
    public String sendMailWithAttachment(EmailRequest request) {
        return null;
    }

    @Override
    public VerificationEmailResponse verifyEmail(VerificationEmailRequest request) {
        String validateStatus = verificationTokenService.validateVerificationToken(request.getToken());
        VerificationEmailResponse response = new VerificationEmailResponse();
        switch (validateStatus) {
            case "OK" -> {
                response.setStatus("OK");
            }
            case "EXPIRED" -> {
                EmailRequest emailRequest = new EmailRequest();
                emailRequest.setRecipient(request.getEmail());
                this.sendSimpleMail(emailRequest);
                response.setStatus("Your activation code has been expired");
            }
            case "INVALID" -> {
                response.setStatus("Your activation code is invalid");
            }
        }
        return response;
    }


}
