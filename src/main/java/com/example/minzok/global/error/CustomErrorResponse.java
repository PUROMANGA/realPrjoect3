package com.example.minzok.global.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder

public class CustomErrorResponse {

    private String message;
    private LocalDateTime timeStamp;

    public CustomErrorResponse(ExceptionCode exceptionCode, String message, LocalDateTime timeStamp) {
        this.message = exceptionCode.getMessage();
        this.timeStamp = LocalDateTime.now();
    }
}
