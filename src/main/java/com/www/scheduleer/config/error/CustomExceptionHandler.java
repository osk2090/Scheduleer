package com.www.scheduleer.config.error;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @ExceptionHandler(value = { CustomException.class })
    public ResponseEntity<ErrorResponse> customExceptionHandler(CustomException e) {
        return ErrorResponse.toResponseEntity(e.getCode());
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, SignatureException.class, MalformedJwtException.class, ExpiredJwtException.class, StringIndexOutOfBoundsException.class})
    public ResponseEntity<ErrorResponse> jwtTokenExceptionHandler() {
        return ErrorResponse.toResponseEntity(ErrorCode.TOKEN_ERROR);
    }
    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<ErrorResponse> exceptionHandler() {
        return ErrorResponse.toResponseEntity(ErrorCode.SERVER_ERROR);
    }
}
