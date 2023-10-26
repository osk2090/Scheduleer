package com.www.scheduleer.config.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //Common error
    DUPLICATE_SUMMONER_NAME(HttpStatus.CONFLICT,"C001", "DUPLICATE_SUMMONER_NAME"),
    POSITION_NOT_FOUND(HttpStatus.NOT_FOUND,"C002","POSITION_NOT_FOUND"),
    EXIST_TEAM(HttpStatus.CONFLICT,"C003","EXIST_TEAM"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"C004","UN_AUTHORIZED"),
    TOKEN_ERROR(HttpStatus.BAD_REQUEST, "C004", "TOKEN_ERROR"),
    BAD_CREDENTIALS(HttpStatus.NOT_FOUND, "C004","BAD_CREDENTIALS"),

    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "C005", "NOT_MATCH_PASSWORD"),
    NOT_MATCH_WRITER(HttpStatus.UNAUTHORIZED, "C006", "NOT_MATCH_WRITER"),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
