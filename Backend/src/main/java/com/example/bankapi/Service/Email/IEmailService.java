package com.example.bankapi.Service.Email;

import com.example.bankapi.DTO.Email.EmailRequest;
import com.example.bankapi.DTO.Email.VerificationEmailRequest;
import com.example.bankapi.DTO.Email.VerificationEmailResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

public interface IEmailService {

    void sendSimpleMail(EmailRequest request);
    String sendMailWithAttachment(EmailRequest request);
    VerificationEmailResponse verifyEmail(VerificationEmailRequest request);
}
