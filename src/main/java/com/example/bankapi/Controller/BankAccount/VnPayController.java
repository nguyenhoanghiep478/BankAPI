package com.example.bankapi.Controller.BankAccount;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/public")
public class VnPayController {
    @GetMapping("/vnpay_return")
    public String vnpayReturn(Model model, @RequestParam Map<String,String> allParams){
        String vnp_secureHash = allParams.get("vnp_secureHash");
        List<String> fieldNames = new ArrayList<>(allParams.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = allParams.get(fieldName);
            if(fieldValue != null && !fieldValue.isEmpty()){
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(fieldValue);
                hashData.append('&');
            }
        }
        String vnp_SecureHashCheck = HmacSHA512("GMO5OBYLAHL6HZ0ID16XOVHC90YLGK4F",hashData.toString());
        if(vnp_secureHash.equals(vnp_SecureHashCheck)){
            model.addAttribute("message", "Payment Successful");
            return "Success";
        }
        model.addAttribute("message", "Payment Failed");
        return "Failed";

    }
    private String HmacSHA512(String key,String data){
        try{
            if(key == null || key.isEmpty()){
                throw new NullPointerException();
            }
            Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] keyBytes = key.getBytes();
            final SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "HmacSHA512");
            hmac512.init(secretKeySpec);
            byte[] dataBytes = data.getBytes();
            byte[] hash = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
