package com.example.minzok.global.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder

public class CustomErrorResponse {

    private String message;
    private LocalDateTime timeStamp;
}
