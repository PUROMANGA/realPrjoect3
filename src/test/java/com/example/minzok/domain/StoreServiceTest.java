package com.example.minzok.domain;

import com.example.minzok.global.error.CustomNullPointerException;
import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.member.entity.Member;
import com.example.minzok.member.enums.UserRole;
import com.example.minzok.member.repository.MemberRepository;
import com.example.minzok.store.dto.StoreRequestDto;
import com.example.minzok.store.service.StoreServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)

public class StoreServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private StoreServiceImpl storeService;

    @Test
    @DisplayName("가게를 등록하려고 하는데 유저가 없음")
    public void cantFindMemberDuringPostingStore(){

        //given

        String email = "example@email.com";
        StoreRequestDto storeRequestDto = new StoreRequestDto(
                "둘이먹다 하나가 죽은 호식이치킨",
                "정말 죽어서 경찰서 갔다왔습니다.",
                LocalTime.parse("09:00"),
                LocalTime.parse("23:00"),
               15000
        );

        given(memberRepository.findMemberByEmail(anyString())).willReturn(Optional.empty());

        //when
        CustomNullPointerException exception = assertThrows(CustomNullPointerException.class, () -> {
            storeService.createStoreService(storeRequestDto, email);
        });

        //then
        assertEquals(ExceptionCode.CANT_FIND_MEMBER.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("정상적인 가게 등록")
    public void canPostStore() {

        //given
        StoreRequestDto storeRequestDto = new StoreRequestDto(
                "둘이먹다 하나가 죽은 호식이치킨",
                "정말 죽어서 경찰서 갔다왔습니다.",
                LocalTime.parse("09:00"),
                LocalTime.parse("23:00"),
                15000
        );

        Member member = Member.of(
                "example@email.com",
                "pw1234",
                UserRole.USER,
                "SAM",
                "nickname",
                LocalDate.of(1995, 4, 24),
                "주소"
        );

        //when

        //then

    }
}
