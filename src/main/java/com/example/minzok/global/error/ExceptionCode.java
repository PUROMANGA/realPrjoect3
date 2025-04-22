package com.example.minzok.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor

public enum ExceptionCode implements ErrorCode{

    VALID_EXCEPTION(HttpStatus.BAD_REQUEST, "VALID_EXCEPTION가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus gethttpStatus() {
        return httpStatus;
    }
}
