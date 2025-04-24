package com.example.minzok.member.service;

import com.example.minzok.global.jwt.MyUserDetail;
import com.example.minzok.member.dto.response.MyMemberResponseDto;
import com.example.minzok.member.dto.response.OtherMemberResponseDto;

public interface MemberService {

    MyMemberResponseDto findMe(MyUserDetail myUserDetail);
    OtherMemberResponseDto findOther(Long id, MyUserDetail myUserDetail);


}
