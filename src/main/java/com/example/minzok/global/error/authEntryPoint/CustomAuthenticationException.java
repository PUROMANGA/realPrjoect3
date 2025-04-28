package com.example.minzok.global.error.authEntryPoint;

import com.example.minzok.global.error.ExceptionCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;


@Getter
public class CustomAuthenticationException extends AuthenticationException {

    private final ExceptionCode exceptionCode;

    public CustomAuthenticationException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
