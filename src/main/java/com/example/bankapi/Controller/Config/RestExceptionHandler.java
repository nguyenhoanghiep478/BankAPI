package com.example.bankapi.Controller.Config;

import com.example.bankapi.Config.GlobalConfig.StaticVar;
import com.example.bankapi.Controller.Config.ExceptionHandle.InsufficientBalanceException;
import com.example.bankapi.Controller.Config.ExceptionHandle.InvalidAccountTypeException;
import com.example.bankapi.Controller.Config.ExceptionHandle.UserEmailExistedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.nio.file.AccessDeniedException;


@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {
    @ExceptionHandler({BadCredentialsException.class, AccessDeniedException.class,
            SignatureException.class})
    public ResponseEntity<ProblemDetail> handleSecurityException(Exception ex, HttpServletRequest request, HttpServletResponse response){
        ProblemDetail errorDetail;
        HttpStatus status;
        String accessDeniedReason;

        if (ex instanceof BadCredentialsException) {
            status = HttpStatus.UNAUTHORIZED;
            accessDeniedReason = StaticVar.UNAUTHORIZED_EXCEPTION_MESSAGE;
        } else if (ex instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
            accessDeniedReason = ex.getMessage();
        } else if (ex instanceof SignatureException) {
            status = HttpStatus.FORBIDDEN;
            accessDeniedReason = StaticVar.SIGNATURE_EXCEPTION_MESSAGE;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            accessDeniedReason = StaticVar.INTERNAL_SERVER_ERROR_MESSAGE;
        }

        errorDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        errorDetail.setProperty(StaticVar.ACCESS_DENIED_PROPERTY_REASON, accessDeniedReason);
        return ResponseEntity.status(status).header(response.getHeader("New-access-token")).body(errorDetail);
    }
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<String> handleInsufficientBalanceException(InsufficientBalanceException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidAccountTypeException.class)
    public ResponseEntity<String> handleInvalidAccountTypeException(InvalidAccountTypeException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserEmailExistedException.class)
    public ResponseEntity<String> handleUserEmailExistedException(UserEmailExistedException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

}
