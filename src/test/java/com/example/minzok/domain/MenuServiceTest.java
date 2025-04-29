package com.example.minzok.domain;

import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.member.entity.Member;
import com.example.minzok.member.enums.UserRole;
import com.example.minzok.member.repository.MemberRepository;
import com.example.minzok.menu.Dto.Request.MenuChangeStauts;
import com.example.minzok.menu.Dto.Request.MenuRequestDto;
import com.example.minzok.menu.Dto.Response.MenuResponseDto;
import com.example.minzok.menu.Entity.Menu;
import com.example.minzok.menu.Repository.MenuRepository;
import com.example.minzok.menu.Service.MenuService;
import com.example.minzok.menu.common.MenuHandler;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.entity.StoreStatus;
import com.example.minzok.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)

public class MenuServiceTest {

    MenuRequestDto menuRequestDto = new MenuRequestDto(
            "김승준의 맛잇게 구운 닭다리",
            10000L,
            "맛없어서 호평"
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

    MenuChangeStauts menuChangeStauts = new MenuChangeStauts();
    Menu menu = new Menu();

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private MenuHandler menuHandler;

    @InjectMocks
    private MenuService menuService;

    @Captor
    private ArgumentCaptor<Menu> menuCaptor;

    /**
     * createdMenuService 실패 테스트
     */
    @Test
    @DisplayName("createdMenuService에서 sotreId를 찾는데 없음")
    public void cantFindStore() {
        //given

        given(storeRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                menuService.createdMenuService(menuRequestDto, store.getId(), managerMember.getEmail()));

        //then

        assertEquals("가게가 없습니다", exception.getMessage());
    }

    /**
     * createdMenuService .성공 테스트
     */
    @Test
    @DisplayName("createdMenuService를 잘 수행함")
    public void canCreatedMenuService() {

        Menu menu = new Menu(menuRequestDto, store);
        menu.setId(1L);
        //given

        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));
        given(menuRepository.save(any(Menu.class))).willReturn(menu);

        //when
        MenuResponseDto result = menuService.createdMenuService(menuRequestDto, store.getId(), managerMember.getEmail());

        //then
        assertNotNull(result);
    }

    /**
     * findModifyMenuService 실패 테스트1
     */
    @Test
    @DisplayName("findModifyMenuService에서 sotreId를 찾는데 없음")
    public void cantFindStoreInFindModifyMenuService() {
        //given

        given(storeRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        menu.setId(1L);
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                menuService.findModifyMenuService(menuChangeStauts, store.getId(), menu.getId(), managerMember.getEmail()));

        //then

        assertEquals("가게가 없습니다", exception.getMessage());
    }

    /**
     * findModifyMenuService 실패 테스트2
     */
    @Test
    @DisplayName("findModifyMenuService에서 menuId를 찾는데 없음")
    public void cantFindMenuInFindModifyMenuService() {
        //given

        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));
        given(menuRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        menu.setId(1L);
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                menuService.findModifyMenuService(menuChangeStauts, store.getId(), menu.getId(), managerMember.getEmail()));

        //then

        assertEquals("메뉴가 없습니다", exception.getMessage());
    }

    /**
     * findModifyMenuService .성공 테스트
     */
    @Test
    @DisplayName("findModifyMenuService를 잘 수행함")
    public void canFindModifyMenuService() {

        Menu menu = new Menu(menuRequestDto, store);
        menu.setId(1L);
        //given

        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));
        given(menuRepository.findById(anyLong())).willReturn(Optional.of(menu));
        given(menuRepository.save(any(Menu.class))).willReturn(menu);

        //when
        MenuResponseDto result = menuService.findModifyMenuService(menuChangeStauts, store.getId(), menu.getId(), managerMember.getEmail());

        //then
        assertNotNull(result);
    }

    /**
     * deleteMenuService 실패 테스트1
     */
    @Test
    @DisplayName("deleteMenuService에서 가게를 찾는데 없음")
    public void cantFindStoreInDeleteMenuService() {
        //given

        given(storeRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        menu.setId(1L);
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                menuService.deleteMenuService(menu.getId(), store.getId(), managerMember.getEmail()));


        //then

        assertEquals("가게가 없습니다", exception.getMessage());
    }

    /**
     * deleteMenuService 실패 테스트2
     */
    @Test
    @DisplayName("deleteMenuService에서 menuId를 찾는데 없음")
    public void cantFindMenuInDeleteMenuService() {
        //given

        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));
        given(menuRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        menu.setId(1L);
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                menuService.deleteMenuService(menu.getId(), store.getId(), managerMember.getEmail()));


        //then

        assertEquals("메뉴가 없습니다", exception.getMessage());
    }

    /**
     * deleteMenuService : 성공 테스트
     */
    @Test
    @DisplayName("deleteMenuService를 잘 수행함")
    public void canDeleteMenuService() {

        Menu menu = new Menu(menuRequestDto, store);
        menu.setId(1L);
        //given

        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));
        given(menuRepository.findById(anyLong())).willReturn(Optional.of(menu));

        //when
        menuService.deleteMenuService(menu.getId(), store.getId(), managerMember.getEmail());
        verify(menuRepository).save(menuCaptor.capture());
        Menu savedMenu = menuCaptor.getValue();

        //then
        assertNotNull(savedMenu);
    }

}
