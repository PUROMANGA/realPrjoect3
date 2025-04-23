package com.example.minzok.global.error;

import lombok.Getter;

@Getter

public class CustomNullPointerException extends NullPointerException {

    private final ExceptionCode exceptionCode;

    public CustomNullPointerException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
