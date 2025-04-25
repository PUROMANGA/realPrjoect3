package com.example.minzok.menu.common;

import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.member.entity.Member;
import com.example.minzok.member.repository.MemberRepository;
import com.example.minzok.menu.Entity.Menu;
import com.example.minzok.menu.Entity.MenuStatus;
import com.example.minzok.menu.Repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class MenuHandler {

    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;

    public void findMemberAndException(String email) {

        Member member = memberRepository.findMemberByEmail(email).orElseThrow(() -> new CustomRuntimeException(ExceptionCode.CANT_FIND_MEMBER));

        if(!member.getEmail().equals(email)) {
            throw new CustomRuntimeException(ExceptionCode.NO_EDIT_PERMISSION);
        }
    }

    public void createMenu(Menu menu) {
        menu.setMenuStatus(MenuStatus.NORMAL);
    }

    public void deleteMenu(Menu menu) {
        menu.setMenuStatus(MenuStatus.DELETE);
    }
}
