package com.example.bankapi.DTO.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    NO_CODE(0,HttpStatus.NOT_IMPLEMENTED,"No code"),
    INCORRECT_CURRENT_PASSWORD(300,HttpStatus.BAD_REQUEST,"Current password is incorrect"),
    NEW_PASSWORD_DOES_NOT_MATCH(301,HttpStatus.BAD_REQUEST,"New password does not match"),
    ACCOUNT_LOCKED(302,HttpStatus.FORBIDDEN,"Account is locked"),
    ACCOUNT_DISABLE(303,HttpStatus.FORBIDDEN,"Account is disabled"),
    BAD_CREDENTIALS(304,HttpStatus.FORBIDDEN,"Login and/or password is incorrect"),
    EMAIL_EXISTED(305,HttpStatus.CONFLICT,"Email already existed"),
    ;
    private final int code;
    private final String description;
    private final HttpStatus httpStatus;
    ErrorCode(final int code, final HttpStatus httpStatus, String description ) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
