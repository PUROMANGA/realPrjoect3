package com.example.minzok.auth.dto.response;

import lombok.Getter;

@Getter
public class TokenResponseDto {

    private final String bearerToken;

    public TokenResponseDto(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}
