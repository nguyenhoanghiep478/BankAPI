package com.example.bankapi.Controller.Config;

import com.example.bankapi.Config.GlobalConfig.StaticVar;
<<<<<<< HEAD
import com.example.bankapi.Controller.Config.ExceptionHandle.InsufficientBalanceException;
import com.example.bankapi.Controller.Config.ExceptionHandle.InvalidAccountTypeException;
import com.example.bankapi.Controller.Config.ExceptionHandle.UserEmailExistedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
=======
import com.example.bankapi.ExceptionHandle.InsufficientBalanceException;
import com.example.bankapi.ExceptionHandle.InvalidAccountTypeException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
>>>>>>> 8bf2d517198aeb5b5c93bc71d0821bb6b2eddbb3
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

<<<<<<< HEAD
import java.io.IOException;
=======
>>>>>>> 8bf2d517198aeb5b5c93bc71d0821bb6b2eddbb3
import java.nio.file.AccessDeniedException;


@RestControllerAdvice
<<<<<<< HEAD
@RequiredArgsConstructor
public class RestExceptionHandler {
    @ExceptionHandler({BadCredentialsException.class, AccessDeniedException.class,
            SignatureException.class})
    public ResponseEntity<ProblemDetail> handleSecurityException(Exception ex, HttpServletRequest request, HttpServletResponse response){
=======
public class RestExceptionHandler {
    @ExceptionHandler({BadCredentialsException.class, AccessDeniedException.class,
            SignatureException.class, ExpiredJwtException.class})
    public ResponseEntity<ProblemDetail> handleSecurityException(Exception ex){
>>>>>>> 8bf2d517198aeb5b5c93bc71d0821bb6b2eddbb3
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
<<<<<<< HEAD
=======
        } else if (ex instanceof ExpiredJwtException) {
            status = HttpStatus.FORBIDDEN;
            accessDeniedReason = StaticVar.EXPIRED_JWT_EXCEPTION_MESSAGE;
>>>>>>> 8bf2d517198aeb5b5c93bc71d0821bb6b2eddbb3
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            accessDeniedReason = StaticVar.INTERNAL_SERVER_ERROR_MESSAGE;
        }

        errorDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        errorDetail.setProperty(StaticVar.ACCESS_DENIED_PROPERTY_REASON, accessDeniedReason);

<<<<<<< HEAD
        return ResponseEntity.status(status).header(response.getHeader("New-access-token")).body(errorDetail);
=======
        return ResponseEntity.status(status).body(errorDetail);
>>>>>>> 8bf2d517198aeb5b5c93bc71d0821bb6b2eddbb3
    }
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<String> handleInsufficientBalanceException(InsufficientBalanceException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidAccountTypeException.class)
    public ResponseEntity<String> handleInvalidAccountTypeException(InvalidAccountTypeException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
<<<<<<< HEAD
    @ExceptionHandler(UserEmailExistedException.class)
    public ResponseEntity<String> handleUserEmailExistedException(UserEmailExistedException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }


=======
>>>>>>> 8bf2d517198aeb5b5c93bc71d0821bb6b2eddbb3
}
