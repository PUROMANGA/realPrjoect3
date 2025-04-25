package com.example.minzok.menu.Controller;


import com.example.minzok.auth.entity.MyUserDetail;
import com.example.minzok.menu.Dto.Request.MenuRequestDto;
import com.example.minzok.menu.Dto.Response.MenuResponseDto;
import com.example.minzok.menu.Service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

public class MenuController {

    private final MenuService menuService;

    /**
     * 메뉴 등록
     * @param menuRequestDto
     * @param storeId
     * @return
     */

    @PostMapping("/{storeId}/menus")
    public ResponseEntity<MenuResponseDto> createdMenu(@RequestBody @Validated MenuRequestDto menuRequestDto,
                                                       @PathVariable Long storeId,
                                                       @AuthenticationPrincipal MyUserDetail myUserDetail) {
        return ResponseEntity.ok(menuService.createdMenuService(menuRequestDto, storeId, myUserDetail.getUsername()));
    }

    /**
     * 메뉴수정
     * @param menuRequestDto
     * @param myUserDetail
     * @return
     */

    @PatchMapping("/{storeId}/{menuId}")
    public ResponseEntity<MenuResponseDto> findModifyMenu(@RequestBody @Validated MenuRequestDto menuRequestDto,
                                                      @PathVariable Long storeId, @PathVariable Long menuId,
                                                          @AuthenticationPrincipal MyUserDetail myUserDetail) {
        return ResponseEntity.ok(menuService.findModifyMenuService(menuRequestDto, storeId, menuId, myUserDetail.getUsername()));
    }

    /**
     * 메뉴 삭제
     * @param menuid
     * @param myUserDetail
     * @return
     */

    @DeleteMapping("/{storeId}/{menuId}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long menuid, @PathVariable Long storeId,
                                           @AuthenticationPrincipal MyUserDetail myUserDetail) {
        menuService.deleteMenuService(menuid, storeId, myUserDetail.getUsername());
        return ResponseEntity.ok().build();
    }
}
