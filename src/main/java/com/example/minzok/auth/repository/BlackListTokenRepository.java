package com.example.minzok.auth.repository;

import com.example.minzok.auth.entity.BlackListToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface BlackListTokenRepository extends JpaRepository<BlackListToken, Long> {
    boolean existsByToken(String token);
    void deleteByExpireDateBefore(LocalDateTime now);
}
