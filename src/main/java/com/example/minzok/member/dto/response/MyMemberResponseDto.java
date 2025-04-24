package com.example.minzok.member.dto.response;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class MyMemberResponseDto {

    private final Long id;

    private final String email;

    private final String userRole;

    private final String name;

    private final String nickname;

    private final LocalDate birth;

    private final String defaultAddress;

    private final LocalDateTime createTime;

    private final LocalDateTime modifiedTime;


    public MyMemberResponseDto(
            Long id,
            String email,
            String userRole,
            String name,
            String nickname,
            LocalDate birth,
            String defaultAddress,
            LocalDateTime createTime,
            LocalDateTime modifiedTime) {
        this.id = id;
        this.email = email;
        this.userRole = userRole;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.defaultAddress = defaultAddress;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
    }


}
