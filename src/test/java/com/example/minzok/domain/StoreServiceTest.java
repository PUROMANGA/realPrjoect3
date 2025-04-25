package com.example.minzok.domain;

import com.example.minzok.global.error.CustomNullPointerException;
import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.member.entity.Member;
import com.example.minzok.member.enums.UserRole;
import com.example.minzok.member.repository.MemberRepository;
import com.example.minzok.store.dto.*;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.entity.StoreStatus;
import com.example.minzok.store.handler.StoreServiceHandler;
import com.example.minzok.store.repository.StoreRepository;
import com.example.minzok.store.service.StoreServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)

public class StoreServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private StoreServiceHandler storeServiceHandler;

    @InjectMocks
    private StoreServiceImpl storeService;

    @Captor
    private ArgumentCaptor<Store> storeCaptor;

    StoreRequestDto storeRequestDto = new StoreRequestDto(
            "둘이먹다 하나가 죽은 호식이치킨",
            "정말 죽어서 경찰서 갔다왔습니다.",
            LocalTime.parse("09:00"),
            LocalTime.parse("23:00"),
            15000
    );

    StoreModifyDto storeModifyDto = new StoreModifyDto(
            "둘이먹다 하나가 죽은 호식이치킨",
            "정말 죽어서 경찰서 갔다왔습니다.",
            LocalTime.parse("09:00"),
            LocalTime.parse("23:00"),
            15000,
            StoreStatus.OPEN
    );

    Member userMember = Member.of(
            "example1@email.com",
            "pw1234",
            UserRole.USER,
            "SAM",
            "nickname",
            LocalDate.of(1995, 4, 24)
    );

    Member managerMember = new Member(
            "example2@email.com",
            "pw1234",
            UserRole.MANAGER,
            "SAM",
            "nickname",
            LocalDate.of(1995, 4, 24),
            1
    );



    Store store = new Store(
            1L,
            "둘이먹다 하나가 죽은 호식이치킨",
            "정말 죽어서 경찰서 갔다왔습니다.",
            LocalTime.parse("09:00"),
            LocalTime.parse("23:00"),
            15000,
            StoreStatus.OPEN,
            managerMember
    );

    Long storeId = 1L;

    /**
     * createStoreService : 실패 테스트
     */

    @Test
    @DisplayName("가게를 등록하려고 하는데 유저가 없음")
    public void cantFindMemberDuringPostingStore(){

        //given
        given(memberRepository.findMemberByEmail(anyString())).willReturn(Optional.empty());

        //when
        CustomNullPointerException exception = assertThrows(CustomNullPointerException.class, () -> {
            storeService.createStoreService(storeRequestDto, managerMember.getEmail());
        });

        //then
        assertEquals(ExceptionCode.CANT_FIND_MEMBER.getMessage(), exception.getMessage());
    }

    /**
     * createStoreService : 실패 테스트2
     */

    @Test
    @DisplayName("가게를 3개 초과로 등록하려면 예외 발생")
    public void limitStore(){

        //given
        given(memberRepository.findMemberByEmail(anyString())).willReturn(Optional.of(managerMember));
        given(storeRepository.countByMemberEmail(anyString())).willReturn(4);

        //when
        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> {
            storeService.createStoreService(storeRequestDto, managerMember.getEmail());
        });

        //then
        assertEquals(ExceptionCode.TOO_MANY_STORES.getMessage(), exception.getMessage());
    }

    /**
     * createStoreService : 실패 테스트3
     */

    @Test
    @DisplayName("member의 countStore가 1 안올라감")
    public void dontUpCountStoreInMember(){

        //given

        int before = managerMember.getStoreCount();

        //when
        managerMember.increaseStoreCount();

        //then
        assertNotEquals(managerMember.getStoreCount(), before);
    }

    /**
     * createStoreService : 성공 테스트
     */

    @Test
    @DisplayName("정상적인 가게 등록")
    public void canPostStore() {

        //given
        given(memberRepository.findMemberByEmail(anyString())).willReturn(Optional.of(managerMember));
        given(storeRepository.countByMemberEmail(anyString())).willReturn(0);
        given(storeServiceHandler.changeStoreStatus(any(Store.class))).willReturn(store);

        //when
        StoreMemberDto result = storeService.createStoreService(storeRequestDto, managerMember.getEmail());

        //then

        assertNotNull(result);
    }

    /**
     * patchStore : 전체 흐름 테스트
     */

    @Test
    @DisplayName("patchStore전체 흐름을 테스트 합니다")
    public void patchStoreAllStreamTest() {

        //given
        given(storeServiceHandler.foundStoreAndException(anyLong(), anyString())).willReturn((store));
        given(storeRepository.save(any(Store.class))).willReturn(store);

        //when
        StoreResponseDto result = storeService.patchStore(storeModifyDto, storeId, managerMember.getEmail());

        //then
        assertNotNull(result);
    }

    /**
     * deleteStoreService : 실패 테스트
     */

    @Test
    @DisplayName("member의 countStore가 1 안 내려감")
    public void decreaseStoreCount() {

        //given

        int count = managerMember.getStoreCount();
        //when
        managerMember.decreaseStoreCount();

        //then
        assertNotEquals(count, managerMember);
    }

    /**
     * deleteStoreService : 전체 흐름 테스트
     */

    @Test
    @DisplayName("deleteStoreService 전체 흐름을 테스트 합니다")
    public void deleteStoreServiceAllStreamTest() {

        //given
        given(memberRepository.findMemberByEmail(anyString())).willReturn(Optional.of(managerMember));
        given(storeServiceHandler.foundStoreAndException(anyLong(), anyString())).willReturn((store));
        given(storeServiceHandler.deleteStoreStatus(any(Store.class))).willReturn(store);

        //when
        storeService.deleteStoreService(storeId, managerMember.getEmail());

        verify(storeRepository).save(storeCaptor.capture());
        Store savedStore = storeCaptor.getValue();

        //then
        assertEquals(StoreStatus.CRUSH, savedStore.getStoreStatus());
    }

    /**
     * cantFindSerchResult : 실패흐름(keyWord 없음)
     */

    @Test
    @DisplayName("키워드로 검색했는데 키워드에 해당하는 검색 결과가 없을 때")
    public void cantFindSearchResult() {

        String keyword = "치킨";
        Pageable pageable = PageRequest.of(0, 10);

        //given

        given(storeRepository.storeNameFindByKeyword(anyString(), any(Pageable.class))).willReturn(new SliceImpl<>(Collections.emptyList()));

        //when

        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> {
            storeService.findStorePage(keyword, pageable);
        });

        //then
        assertEquals(ExceptionCode.NOT_FIND_KEYWORD.getMessage(), exception.getMessage());
    }

    /**
     * cantFindSerchResult : 성공흐름
     */

    @Test
    @DisplayName("키워드로 검색했는데 키워드에 해당하는 검색 결과가 없을 때")
    public void canFindSearchResult() {

        String keyword = "치킨";
        Pageable pageable = PageRequest.of(0, 10);
        List<Store> storeList = List.of(store);

        //given

        given(storeRepository.storeNameFindByKeyword(anyString(), any(Pageable.class))).willReturn(new SliceImpl<>(storeList));

        //when

        Slice<OnlyStoreResponseDto> result = storeService.findStorePage(keyword, pageable);

        //then
        assertNotNull(result);
    }

    /**
     * findOneStore : 실패흐름(Store없음)
     */

    @Test
    @DisplayName("특정 가게를 조회했는데 그 가게가 없음")
    public void cantFindStoreResult() {

        Pageable pageable = PageRequest.of(0, 10);

        //given

        given(storeRepository.menuFindById(anyLong(), any(Pageable.class))).willReturn(new SliceImpl<>(Collections.emptyList()));

        //when

        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> {
            storeService.findOneStore(store.getId(), pageable);
        });

        //then
        assertEquals(ExceptionCode.CANT_FIND_STORE.getMessage(), exception.getMessage());
    }

    /**
     * findOneStore : 성공흐름
     */

    @Test
    @DisplayName("특정 가게를 조회했는데 그 가게가 있고 메뉴도 같이 나옴")
    public void canFindStoreResult() {

        Pageable pageable = PageRequest.of(0, 10);

        //given

        given(storeRepository.menuFindById(anyLong(), any(Pageable.class))).willReturn(new SliceImpl<>(Collections.emptyList()));

        //when

        Slice<StoreResponseDto> result = storeRepository.menuFindById(store.getId(), pageable);

        //then
        assertNotNull(result);
    }
}
