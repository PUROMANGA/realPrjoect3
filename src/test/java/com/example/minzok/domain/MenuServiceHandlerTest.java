package com.example.minzok.domain;


import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.member.entity.Member;
import com.example.minzok.member.repository.MemberRepository;
import com.example.minzok.menu.common.MenuHandler;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.entity.StoreStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)

public class MenuServiceHandlerTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MenuHandler menuHandler;

    @Captor
    private ArgumentCaptor<Member> memberCaptor;

    String anotherEmail = "another@exampl.com";

    Member member = new Member(
            "abcd@exampl.com"
    );

    /**
     * findMemberAndException 실패 테스트
     */

    @Test
    @DisplayName("email로 등록된 member가 존재하지 않습니다")
    public void findMemberButHasNot() {

        //given

        given(memberRepository.findMemberByEmail(anyString())).willReturn(Optional.empty());

        //when

        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> {
            menuHandler.findMemberAndException(member.getEmail());
        });

        //then

        assertEquals(ExceptionCode.CANT_FIND_MEMBER.getMessage(), exception.getMessage());

    }

    @Test
    @DisplayName("email이 달라서 수정이 불가능함")
    public void notSameEamil() {

        //given
        given(memberRepository.findMemberByEmail(anyString())).willReturn(Optional.of(member));

        //when
        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> {
            menuHandler.findMemberAndException(anotherEmail);
        });

        //then
        assertEquals(ExceptionCode.NO_EDIT_PERMISSION.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("정상적인 테스트")
    public void findMemberAndSuccess() {

        //given

        //when

        //then
    }
}
