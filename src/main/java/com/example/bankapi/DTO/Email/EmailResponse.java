package com.example.bankapi.DTO.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailResponse {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
