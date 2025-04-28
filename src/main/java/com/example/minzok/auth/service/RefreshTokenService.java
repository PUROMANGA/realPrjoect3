package com.example.minzok.auth.service;

import com.example.minzok.auth.dto.response.TokenResponseDto;
import com.example.minzok.auth.entity.RefreshToken;
import com.example.minzok.auth.repository.RefreshTokenRepository;
import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.global.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void createRefreshToken(String email){
        String refreshTokenCode = jwtUtil.createRefreshToken(email);
        LocalDateTime expiredDate = jwtUtil.extractExpiration(refreshTokenCode);
        RefreshToken refreshToken = RefreshToken.of(refreshTokenCode, expiredDate, email);
        refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public TokenResponseDto refresh(String refreshToken){
        if(!jwtUtil.validateToken(refreshToken)){
            throw new CustomRuntimeException(ExceptionCode.INVALID_REFRESH_TOKEN);
        }
        String email;
        try {
            Claims claims = jwtUtil.extractClaims(refreshToken);
            email = claims.getSubject();
        } catch (JwtException e) {
            throw new CustomRuntimeException(ExceptionCode.INVALID_REFRESH_TOKEN);
        }

        RefreshToken savedRefreshToken = refreshTokenRepository.findByEmail(email)
                .orElseThrow(() -> new CustomRuntimeException(ExceptionCode.INVALID_REFRESH_TOKEN));

        if (!refreshToken.equals(savedRefreshToken.getToken())) {
            throw new CustomRuntimeException(ExceptionCode.INVALID_REFRESH_TOKEN);
        }

        return new TokenResponseDto(jwtUtil.createToken(email));
    }

    @Transactional
    public void deleteRefreshToken(String token){
        String email;
        try {
            Claims claims = jwtUtil.extractClaims(token);
            email = claims.getSubject();
        } catch (JwtException e) {
            throw new CustomRuntimeException(ExceptionCode.INVALID_REFRESH_TOKEN);
        }
        RefreshToken refreshToken = refreshTokenRepository.findByEmail(email).orElseThrow(
                () -> new CustomRuntimeException(ExceptionCode.INVALID_REFRESH_TOKEN));
        if (!token.equals(refreshToken.getToken())) {
            throw new CustomRuntimeException(ExceptionCode.INVALID_REFRESH_TOKEN);
        }
        refreshTokenRepository.delete(refreshToken);
    }


    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정
    @Transactional
    public void deleteExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        refreshTokenRepository.deleteByExpireDateBefore(now);
    }




}
