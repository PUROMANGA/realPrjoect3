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

    Member member = Member.of(
            "example@email.com",
            "pw1234",
            UserRole.USER,
            "SAM",
            "nickname",
            LocalDate.of(1995, 4, 24)
    );

    Store store = new Store(
            1L,
            "둘이먹다 하나가 죽은 호식이치킨",
            "정말 죽어서 경찰서 갔다왔습니다.",
            LocalTime.parse("09:00"),
            LocalTime.parse("23:00"),
            15000,
            StoreStatus.OPEN,
            member
    );

    Long storeId = 1L;

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
            storeService.createStoreService(storeRequestDto, member.getEmail());
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
        given(memberRepository.findMemberByEmail(anyString())).willReturn(Optional.of(member));
        given(storeRepository.countByMemberEmail(anyString())).willReturn(4);

        //when
        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> {
            storeService.createStoreService(storeRequestDto, member.getEmail());
        });

        //then
        assertEquals(ExceptionCode.TOO_MANY_STORES.getMessage(), exception.getMessage());
    }

    /**
     * createStoreService : 실패 테스트3
     */

    @Test
    @DisplayName("등록하려는 유저가 권한이 USER일 경우")
    public void userCantHaveAuth(){

        //given
        given(memberRepository.findMemberByEmail(anyString())).willReturn(Optional.of(member));
        given(storeRepository.countByMemberEmail(anyString())).willReturn(3);
        member.setUserRole(UserRole.USER);

        //when
        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> {
            storeService.createStoreService(storeRequestDto, member.getEmail());
        });

        //then
        assertEquals(ExceptionCode.NO_HAVE_PERMISSION.getMessage(), exception.getMessage());
    }

    /**
     * createStoreService : 성공 테스트
     */

    @Test
    @DisplayName("정상적인 가게 등록")
    public void canPostStore() {

        //given
        given(memberRepository.findMemberByEmail(anyString())).willReturn(Optional.of(member));
        given(storeRepository.countByMemberEmail(anyString())).willReturn(3);
        given(storeRepository.save(any(Store.class))).willReturn(store);
        member.setUserRole(UserRole.MANAGER);

        //when
        StoreResponseDto result = storeService.createStoreService(storeRequestDto, member.getEmail());

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
        StoreResponseDto result = storeService.patchStore(storeRequestDto, storeId, member.getEmail());

        //then
        assertNotNull(result);
    }

    /**
     * deleteStoreService : 전체 흐름 테스트
     */

    @Test
    @DisplayName("deleteStoreService 전체 흐름을 테스트 합니다")
    public void deleteStoreServiceAllSteramTest() {

        //given
        given(storeServiceHandler.foundStoreAndException(anyLong(), anyString())).willReturn((store));
        given(storeRepository.save(any(Store.class))).willReturn(store);

        //when
        storeService.deleteStoreService(storeId, member.getEmail());

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
    public void cantFindSerchResult() {

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
    public void canFindSerchResult() {

        String keyword = "치킨";
        Pageable pageable = PageRequest.of(0, 10);
        List<Store> storeList = List.of(store);

        //given

        given(storeRepository.storeNameFindByKeyword(anyString(), any(Pageable.class))).willReturn(new SliceImpl<>(storeList));

        //when

        Slice<StoreResponseDto> result = storeService.findStorePage(keyword, pageable);

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
