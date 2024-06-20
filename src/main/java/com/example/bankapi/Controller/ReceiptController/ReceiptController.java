package com.example.bankapi.Controller.ReceiptController;

import com.example.bankapi.DTO.RECEIPT.ReceiptCreateRequest;
import com.example.bankapi.DTO.RECEIPT.ReceiptCreateResponse;
import com.example.bankapi.Service.BankService.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receipt")
@RequiredArgsConstructor
public class ReceiptController {
    private final ReceiptService receiptService;

    @PostMapping("/create")
    public ResponseEntity<ReceiptCreateResponse> createReceipt(@RequestBody ReceiptCreateRequest request){
        return ResponseEntity.ok(receiptService.create(request));
    }

}
