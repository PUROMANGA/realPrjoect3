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

    /**
     * 제공받은 토큰을 유효성 검사하고 블랙리스트로 등록합니다.
     * @param token
     */
    @Transactional
    public void addToBlacklist(String token){

        LocalDateTime expireDate = jwtUtil.extractExpiration(token);
        BlackListToken blackListToken = BlackListToken.of(token, expireDate);

        blackListTokenRepository.save(blackListToken);
    }

    /**
     * 제공받은 토큰이 블랙리스트인지 확인한다.
     * @param token
     * @return
     */
    public boolean isTokenBlacklisted(String token) {
        return blackListTokenRepository.existsByToken(token);
    }

    /**
     * 정시마다 만료된 블랙리스트 토큰을 삭제한다.
     */
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정
    @Transactional
    public void deleteExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        blackListTokenRepository.deleteByExpireDateBefore(now);
    }

}
