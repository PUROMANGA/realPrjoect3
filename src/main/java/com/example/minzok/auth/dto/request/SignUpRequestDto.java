package com.example.minzok.auth.dto.request;

import com.example.minzok.member.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignUpRequestDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String passwordCheck;

    @NotBlank
    private String userRole;

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    @NotBlank
    private LocalDate birth;

    @NotBlank
    private String lotNumberAddress;

    @NotBlank
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
