package com.example.minzok.auth.dto.request;

import com.example.minzok.member.enums.UserRole;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

    private String email;

    private String password;

    private String passwordCheck;

    private String userRole;

    private String name;

    private String nickname;

    private String birth;

    private String address;
}
