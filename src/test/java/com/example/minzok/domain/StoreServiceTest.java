package com.example.minzok.domain;

import com.example.minzok.global.error.CustomNullPointerException;
import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.member.entity.Member;
import com.example.minzok.member.enums.UserRole;
import com.example.minzok.member.repository.MemberRepository;
import com.example.minzok.store.dto.StoreRequestDto;
import com.example.minzok.store.dto.StoreResponseDto;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.repository.StoreRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)

public class StoreServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreServiceImpl storeService;


    Long storeId = 1L;

    String email = "example@email.com";

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
            LocalDate.of(1995, 4, 24)
    );

    /**
     * createStoreService : 실패 테스트
     */

    @Test
    @DisplayName("가게를 등록하려고 하는데 유저가 없음")
    public void cantFindMemberDuringPostingStore(){

        //given

        String email = "example@email.com";

        given(memberRepository.findMemberByEmail(anyString())).willReturn(Optional.empty());

        //when
        CustomNullPointerException exception = assertThrows(CustomNullPointerException.class, () -> {
            storeService.createStoreService(storeRequestDto, email);
        });

        //then
        assertEquals(ExceptionCode.CANT_FIND_MEMBER.getMessage(), exception.getMessage());
    }

    /**
     * createStoreService : 성공 테스트
     */

    @Test
    @DisplayName("정상적인 가게 등록")
    public void canPostStore() {

        //given

        given(memberRepository.findMemberByEmail(anyString())).willReturn(Optional.of(member));

        //when

        StoreResponseDto result = storeService.createStoreService(storeRequestDto, member.getEmail());

        //then

        assertNotNull(result);
    }

    /**
     * patchStore : 실패테스트 1
     */

    @Test
    @DisplayName("가게를 수정하려고 하는데 가게가 없다")
    public void cantFindStore() {

        //given

        given(storeRepository.findById(anyLong())).willReturn(Optional.empty());

        //when

        CustomNullPointerException exception = assertThrows(CustomNullPointerException.class, () -> {
            storeService.patchStore(storeRequestDto, storeId, member.getEmail());
        });

        //then

        assertEquals(ExceptionCode.CANT_FIND_STORE.getMessage(), exception.getMessage());
    }

    /**
     * patchStore : 실패테스트 2
     */

    @Test
    @DisplayName("가게를 수정하려고 하는데 그 email로 등록된 멤버가 보이지 않는다")
    public void cantFindMemberForPuttingStore() {

        //given

        Store store = new Store();

        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));
        given(memberRepository.findMemberByEmail(anyString())).willReturn(Optional.empty());

        //when

        CustomNullPointerException exception = assertThrows(CustomNullPointerException.class, () -> {
            storeService.patchStore(storeRequestDto, storeId, email);
        });

        //then

        assertEquals(ExceptionCode.CANT_FIND_MEMBER.getMessage(), exception.getMessage());
    }

    /**
     * patchStore : 실패테스트 3
     */

    @Test
    @DisplayName("가게를 수정하려고 하는데 다른 사람이 가게를 수정하려고 한다")
    public void youAreNotSameMan() {

        //given

        Store store = new Store();

        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));
        given(memberRepository.findMemberByEmail(anyString())).willReturn(Optional.of(member));

        //when

        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class,() -> {
            storeService.patchStore(storeRequestDto, storeId, email);
                });

        //then

        assertEquals(ExceptionCode.NO_EDIT_PERMISSION.getMessage(), exception.getMessage());
    }

    /**
     * patchStore : 성공 케이스
     */

    @Test
    @DisplayName("수정을 하였습니다")
    public void youCanUpdateStoreInterface() {

        //given

        Store store = new Store();

        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));
        given(memberRepository.findMemberByEmail(anyString())).willReturn(Optional.of(member));

        //when

        StoreResponseDto result = storeService.patchStore(storeRequestDto, storeId, member.getEmail());

        //then

        assertNotNull(result);
    }
}
