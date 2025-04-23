package com.example.minzok.auth.service;

import com.example.minzok.auth.entity.BlackListToken;
import com.example.minzok.auth.repository.BlackListTokenRepository;
import com.example.minzok.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BlackListTokenService {

    private final BlackListTokenRepository blackListTokenRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void addToBlacklist(String token){
        LocalDateTime expireDate = jwtUtil.extractExpiration(token);
        BlackListToken blackListToken = BlackListToken.of(token, expireDate);
    }

    public boolean isTokenBlacklisted(String token) {
        return blackListTokenRepository.existsByToken(token);
    }

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정
    @Transactional
    public void deleteExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        blackListTokenRepository.deleteByExpireDateBefore(now);
    }

}
