package com.example.minzok.domain;

import com.example.minzok.global.error.CustomNullPointerException;
import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.member.repository.MemberRepository;
import com.example.minzok.store.dto.StoreRequestDto;
import com.example.minzok.store.service.StoreServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)

public class StoreServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private StoreServiceImpl storeService;

    @Test
    @DisplayName("유저가 없음")
    public void cantFindUserDuringStore(){

        //given

        long memberId = 1;
        StoreRequestDto storeRequestDto = new StoreRequestDto(
                "둘이먹다 하나가 죽은 호식이치킨",
                "정말 죽어서 경찰서 갔다왔습니다.",
                LocalTime.parse("09:00"),
                LocalTime.parse("23:00"),
               15000
        );

        given(memberRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        CustomNullPointerException exception = assertThrows(CustomNullPointerException.class, () -> {
            storeService.createStoreService(storeRequestDto, memberId);
        });

        //then
        assertEquals(ExceptionCode.CANT_FIND_MEMBER.getMessage(), exception.getMessage());
    }
}
