package com.example.minzok.member.dto.request;

import lombok.Getter;

@Getter
public class MemberDeleteRequestDto {

    private String password;

    public MemberDeleteRequestDto() {
    }

    public MemberDeleteRequestDto(String password) {
        this.password = password;
    }
}
