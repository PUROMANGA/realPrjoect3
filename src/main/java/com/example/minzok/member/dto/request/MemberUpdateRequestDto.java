package com.example.minzok.member.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberUpdateRequestDto {

    @Pattern(regexp = ".*\\S.*", message = "공백만으로 이루어질 수 없습니다.")
    private String oldPassword;

    @Pattern(regexp = ".*\\S.*", message = "공백만으로 이루어질 수 없습니다.")
    private String newPassword;

    @Pattern(regexp = ".*\\S.*", message = "공백만으로 이루어질 수 없습니다.")
    private String newPasswordCheck;

    @Pattern(regexp = ".*\\S.*", message = "공백만으로 이루어질 수 없습니다.")
    private String nickname;

    private LocalDate birth;


    public MemberUpdateRequestDto() {
    }

    public MemberUpdateRequestDto(
            String oldPassword,
            String newPassword,
            String newPasswordCheck,
            String nickname,
            LocalDate birth
    ) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newPasswordCheck = newPasswordCheck;
        this.nickname = nickname;
        this.birth = birth;
    }
}
