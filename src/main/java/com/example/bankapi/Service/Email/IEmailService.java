package com.example.bankapi.Service.Email;

import com.example.bankapi.DTO.Email.EmailRequest;
import com.example.bankapi.DTO.Email.VerificationEmailRequest;

public interface IEmailService {
    String sendSimpleMail(EmailRequest request);
    String sendMailWithAttachment(EmailRequest request);
    String verifyEmail(VerificationEmailRequest request);
}
