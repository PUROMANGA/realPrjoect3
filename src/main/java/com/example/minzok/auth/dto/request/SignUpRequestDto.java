package com.example.minzok.auth.dto.request;

import com.example.minzok.member.enums.UserRole;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignUpRequestDto {

    private String email;

    private String password;

    private String passwordCheck;

    private String userRole;

    private String name;

    private String nickname;

    private LocalDate birth;

    private String lotNumberAddress;

    private String detailAddress;

    public SignUpRequestDto() {
    }

    public SignUpRequestDto(
            String email,
            String password,
            String passwordCheck,
            String userRole,
            String name,
            String nickname,
            LocalDate birth,
            String lotNumberAddress,
            String detailAddress
    ) {
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.userRole = userRole;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.lotNumberAddress = lotNumberAddress;
        this.detailAddress = detailAddress;
    }
}
