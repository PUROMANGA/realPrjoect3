package com.example.minzok.global.error;

import com.example.minzok.global.error.authEntryPoint.CustomAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice

public class CustomExceptionHandler {

    /**
     * Vaild 예외처리
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleArgumentNotValidException(MethodArgumentNotValidException e) {

        log.error("[ValidException 발생] cause:{}, message: {}",
                NestedExceptionUtils.getMostSpecificCause(e),
                e.getMessage());

        ErrorCode errorCode = ExceptionCode.VALID_EXCEPTION;

        CustomErrorResponse response = CustomErrorResponse.builder()
                .message(errorCode.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<CustomErrorResponse> handleCustomRuntimeException(CustomRuntimeException e) {

        log.error("[RuntimeException 발생] cause:{}, message: {}",
                NestedExceptionUtils.getMostSpecificCause(e),
                e.getMessage());

        ErrorCode errorCode = ExceptionCode.RUNTIME_EXCEPTION;

        CustomErrorResponse response = CustomErrorResponse.builder()
                .message(errorCode.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<CustomErrorResponse> handleException(CustomAuthenticationException e) {

        ErrorCode errorCode = e.getExceptionCode();

        CustomErrorResponse response = CustomErrorResponse.builder()
                .message(errorCode.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {

        log.error("[입력 error 발생] cause:{}, message: {}",
                NestedExceptionUtils.getMostSpecificCause(e),
                e.getMessage());

        ErrorCode errorCode = ExceptionCode.DUPLICATE_SAME_NAME;

        CustomErrorResponse response = CustomErrorResponse.builder()
                .message(errorCode.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(response);
    }
}
