package com.example.minzok.auth.entity;

import com.example.minzok.global.base_entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "blacklisttokens")
public class BlackListToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expireDate;

    protected BlackListToken() {
    }

    private BlackListToken(String token, LocalDateTime expireDate) {
        this.token = token;
        this.expireDate = expireDate;
    }

    public static BlackListToken of(String token, LocalDateTime expireDate){
        return new BlackListToken(token, expireDate);
    }
}
