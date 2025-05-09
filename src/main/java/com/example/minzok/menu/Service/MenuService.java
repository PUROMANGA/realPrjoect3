package com.example.minzok.menu.Service;

import com.example.minzok.global.error.CustomNullPointerException;
import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.member.entity.Member;
import com.example.minzok.member.repository.MemberRepository;
import com.example.minzok.menu.Dto.Request.MenuChangeStauts;
import com.example.minzok.menu.Dto.Request.MenuRequestDto;
import com.example.minzok.menu.Dto.Response.MenuResponseDto;
import com.example.minzok.menu.Entity.Menu;
import com.example.minzok.menu.Entity.MenuStatus;
import com.example.minzok.menu.Repository.MenuRepository;
import com.example.minzok.menu.common.MenuHandler;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class MenuService {

    private final MenuRepository menuRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final MenuHandler menuHandler;

    /**
     * 메뉴등록
     * @param menuRequestDto
     * @param storeId
     * @return
     */

    @Secured({"ROLE_MANAGER"})
    @Transactional
    public MenuResponseDto createdMenuService(MenuRequestDto menuRequestDto, Long storeId, String email) {
        menuHandler.findMemberAndException(email);
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("가게가 없습니다"));
        Menu menu = new Menu(menuRequestDto, store);
        menuHandler.createMenu(menu);
        return new MenuResponseDto(menuRepository.save(menu));
    }

    /**
     * 메뉴수정
     * @param menuRequestDto
     * @param storeId
     * @param menuId
     * @return
     */

    @Secured({"ROLE_MANAGER"})
    @Transactional
    public MenuResponseDto findModifyMenuService(MenuChangeStauts menuChangeStauts, Long storeId, Long menuId, String email) {
        menuHandler.findMemberAndException(email);
        storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("가게가 없습니다"));
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new RuntimeException("메뉴가 없습니다"));
        menu.update(menuChangeStauts);
        return new MenuResponseDto(menuRepository.save(menu));
    }

    /**
     * 메뉴삭제
     * @param menuid
     * @param storeId
     */

    @Secured({"ROLE_MANAGER"})
    @Transactional
    public void deleteMenuService(Long menuid, Long storeId, String email) {
        menuHandler.findMemberAndException(email);
        storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("가게가 없습니다"));
        Menu menu = menuRepository.findById(menuid).orElseThrow(() -> new RuntimeException("메뉴가 없습니다"));
        menuHandler.deleteMenu(menu);
        menuRepository.save(menu);
    }

}
