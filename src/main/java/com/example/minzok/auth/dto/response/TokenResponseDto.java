package com.example.minzok.auth.dto.response;

public class TokenResponseDto {

    private final String bearerToken;

    public TokenResponseDto(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}
