package com.example.bankapi.Service.BankService;

import com.example.bankapi.DTO.RECEIPT.ReceiptCreateRequest;
import com.example.bankapi.DTO.RECEIPT.ReceiptCreateResponse;
import com.example.bankapi.DTO.RECEIPT.getReceiptByAccountNumberRequest;
import com.example.bankapi.DTO.RECEIPT.getReceiptByAccountNumberResponse;

import java.util.List;

public interface IReceiptService {
    ReceiptCreateResponse create(ReceiptCreateRequest request);

    List<getReceiptByAccountNumberResponse> getReceiptByAccountNumber(getReceiptByAccountNumberRequest request);
}
