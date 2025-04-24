package com.example.minzok.member.service;

import com.example.minzok.auth.entity.MyUserDetail;
import com.example.minzok.member.dto.request.MemberDeleteRequestDto;
import com.example.minzok.member.dto.request.MemberUpdateRequestDto;
import com.example.minzok.member.dto.response.MyMemberResponseDto;
import com.example.minzok.member.dto.response.OtherMemberResponseDto;

public interface MemberService {

    MyMemberResponseDto findMe(MyUserDetail myUserDetail);
    OtherMemberResponseDto findOther(Long id, MyUserDetail myUserDetail);
    MyMemberResponseDto updateMember(Long id, MyUserDetail myUserDetail, MemberUpdateRequestDto dto);
    boolean matchMember(Long addressId, String email);
    void deleteMember(MyUserDetail myUserDetail, MemberDeleteRequestDto dto);

}
