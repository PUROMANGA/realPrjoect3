package com.example.minzok.auth.entity;

import com.example.minzok.global.base_entity.BaseEntity;
import com.example.minzok.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "refreshtokens")
public class RefreshToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expireDate;

    @Column(nullable = false)
    private String email;

    protected RefreshToken() {
    }

    private RefreshToken(String token, LocalDateTime expireDate, String email) {
        this.token = token;
        this.expireDate = expireDate;
        this.email = email;
    }

    public static RefreshToken of(String token, LocalDateTime expireDate, String email){
        return new RefreshToken(token, expireDate, email);
    }


}
