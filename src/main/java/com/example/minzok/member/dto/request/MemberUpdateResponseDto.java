package com.example.minzok.member.dto.request;

import com.example.minzok.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberUpdateResponseDto {

    private String oldPassword;

    private String newPassword;

    private String newPasswordCheck;

    private String nickname;

    private String birth;

    private String address;

    public MemberUpdateResponseDto() {
    }

    public MemberUpdateResponseDto(String oldPassword, String newPassword, String newPasswordCheck, String nickname, String birth, String address) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newPasswordCheck = newPasswordCheck;
        this.nickname = nickname;
        this.birth = birth;
        this.address = address;
    }
}
