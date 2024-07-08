package com.example.bankapi.Service.Email.impl;

import com.example.bankapi.Config.GlobalConfig.StaticVar;
import com.example.bankapi.DTO.Email.EmailRequest;
import com.example.bankapi.DTO.Email.VerificationEmailRequest;
import com.example.bankapi.Repositories.Authentication.User;
import com.example.bankapi.Service.Email.IEmailService;
import com.example.bankapi.Service.Email.IVerificationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {
    private final JavaMailSender javaMailSender;
    private final IVerificationToken verificationTokenService;
    private final User userRepository;
    @Override
    public String sendSimpleMail(EmailRequest request) {
        if(request.getAttachment()!=null){
            return sendMailWithAttachment(request);
        }
       try{
           String token =verificationTokenService.generateVerificationToken(userRepository.findByEmail(request.getRecipient()));
           SimpleMailMessage mailMessage = new SimpleMailMessage();
           mailMessage.setFrom(StaticVar.MAIL_SENDER);
           mailMessage.setTo(request.getRecipient());
           mailMessage.setSubject(StaticVar.MAIL_SUBJECT);
           mailMessage.setText(StaticVar.MAIL_TEXT+token);
           javaMailSender.send(mailMessage);
           return "Mail sent successful";
        }catch (Exception e){
           return e.getMessage();

       }
    }

    @Override
    public String sendMailWithAttachment(EmailRequest request) {
        return null;
    }

    @Override
    public String verifyEmail(VerificationEmailRequest request) {
        return verificationTokenService.validateVerificationToken(request.getToken());
    }


}
