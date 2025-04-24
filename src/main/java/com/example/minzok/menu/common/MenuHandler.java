package com.example.minzok.menu.common;

import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.member.entity.Member;
import com.example.minzok.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class MenuHandler {

    private final MemberRepository memberRepository;

    public void findMemberAndException(String email) {

        Member member = memberRepository.findMemberByEmail(email).orElseThrow(() -> new CustomRuntimeException(ExceptionCode.CANT_FIND_MEMBER));

        if(!member.getEmail().equals(email)) {
            throw new CustomRuntimeException(ExceptionCode.NO_EDIT_PERMISSION);
        }
    }
}
