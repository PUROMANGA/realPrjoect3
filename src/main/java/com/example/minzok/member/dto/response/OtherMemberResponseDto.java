package com.example.minzok.member.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OtherMemberResponseDto {

    private final Long id;

    private final String name;

    private final String nickname;

    private final String defaultAddress;

    private final LocalDateTime createTime;

    private final List<MemberStoreOrderCountDto> memberStoreOrderCountDtoList;

    public OtherMemberResponseDto(
            Long id,
            String name,
            String nickname,
            String defaultAddress,
            LocalDateTime createTime,
            List<MemberStoreOrderCountDto> memberStoreOrderCountDtoList) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.defaultAddress = defaultAddress;
        this.createTime = createTime;
        this.memberStoreOrderCountDtoList = memberStoreOrderCountDtoList;
    }
}
