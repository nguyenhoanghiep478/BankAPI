package com.example.bankapi.Controller.Config;

import com.example.bankapi.Config.GlobalConfig.StaticVar;
import com.example.bankapi.Controller.Config.ExceptionHandle.InsufficientBalanceException;
import com.example.bankapi.Controller.Config.ExceptionHandle.InvalidAccountTypeException;
import com.example.bankapi.Controller.Config.ExceptionHandle.UserEmailExistedException;
import com.example.bankapi.DTO.Exception.ErrorCode;
import com.example.bankapi.DTO.Exception.ExceptionResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.HashSet;
import java.util.Set;

import static com.example.bankapi.DTO.Exception.ErrorCode.BAD_CREDENTIALS;
import static com.example.bankapi.DTO.Exception.ErrorCode.EMAIL_EXISTED;


@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {
    @ExceptionHandler({  AccessDeniedException.class,
            SignatureException.class})
    public ResponseEntity<ProblemDetail> handleSecurityException(Exception ex, HttpServletRequest request, HttpServletResponse response){
        ProblemDetail errorDetail;
        HttpStatus status;
        String accessDeniedReason;
         if (ex instanceof AccessDeniedException) {
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
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException exception){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .header("Content-Type", "application/json")
                .body(
                        ExceptionResponse.builder()
                                .errorCode(ErrorCode.ACCOUNT_LOCKED.getCode())
                                .errorDescription(ErrorCode.ACCOUNT_LOCKED.getDescription())
                                .error(exception.getMessage())
                                .build()
                )
                ;
    }
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException exception){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .header("Content-Type", "application/json")
                .body(
                        ExceptionResponse.builder()
                                .errorCode(ErrorCode.ACCOUNT_DISABLE.getCode())
                                .errorDescription(ErrorCode.ACCOUNT_DISABLE.getDescription())
                                .error(exception.getMessage())
                                .build()
                )
                ;
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exception){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .header("Content-Type", "application/json")
                .body(
                        ExceptionResponse.builder()
                                .errorCode(BAD_CREDENTIALS.getCode())
                                .errorDescription(BAD_CREDENTIALS.getDescription())
                                .error(BAD_CREDENTIALS.getDescription())
                                .build()
                )
                ;
    }
    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exception){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header("Content-Type", "application/json")
                .body(
                        ExceptionResponse.builder()
                                .error(exception.getMessage())
                                .build()
                )
                ;
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exception){
        Set<String> errors = new HashSet<>();
        exception.getBindingResult().getAllErrors()
                .forEach(error -> errors.add(error.getDefaultMessage()));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header("Content-Type", "application/json")
                .body(
                        ExceptionResponse.builder()
                                .validationErrors(errors)
                                .build()
                );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exception){
        exception.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header("Content-Type", "application/json")
                .body(
                        ExceptionResponse.builder()
                                .errorDescription("Internal Error,please contact support@gmail.com")
                                .error(exception.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<String> handleInsufficientBalanceException(InsufficientBalanceException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidAccountTypeException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidAccountTypeException(InvalidAccountTypeException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header("Content-Type","application/json")
                .body(
                       ExceptionResponse.builder()
                               .errorDescription(EMAIL_EXISTED.getDescription())
                               .error(ex.getMessage())
                               .build()
                )
                ;
    }
    @ExceptionHandler(UserEmailExistedException.class)
    public ResponseEntity<ExceptionResponse> handleUserEmailExistedException(UserEmailExistedException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header("Content-Type","application/json")
                .body(
                        ExceptionResponse.builder()
                                .errorDescription(EMAIL_EXISTED.getDescription())
                                .error(ex.getMessage())
                                .build()
                )
                ;
    }

}
