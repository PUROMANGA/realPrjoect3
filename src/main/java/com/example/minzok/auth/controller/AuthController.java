package com.example.minzok.auth.controller;

import com.example.minzok.auth.dto.request.LoginRequestDto;
import com.example.minzok.auth.dto.request.SignUpRequestDto;
import com.example.minzok.auth.dto.response.TokenResponseDto;
import com.example.minzok.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<TokenResponseDto> signup(@Valid @RequestBody SignUpRequestDto dto){
        return new ResponseEntity<>(authService.signup(dto), HttpStatus.CREATED);
    }






}
