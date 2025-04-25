package com.example.minzok.menu.Controller;


import com.example.minzok.auth.entity.MyUserDetail;
import com.example.minzok.menu.Dto.Request.MenuChangeStauts;
import com.example.minzok.menu.Dto.Request.MenuRequestDto;
import com.example.minzok.menu.Dto.Response.MenuResponseDto;
import com.example.minzok.menu.Service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")

public class MenuController {

    private final MenuService menuService;

    /**
     * 메뉴 등록
     * @param menuRequestDto
     * @param storeId
     * @return
     */

    @PostMapping("/{storeId}/menus")
    public ResponseEntity<MenuResponseDto> createdMenu(@Valid @RequestBody MenuRequestDto menuRequestDto,
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

    @PatchMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<MenuResponseDto> findModifyMenu(@RequestBody @Validated MenuChangeStauts menuChangeStauts,
                                                      @PathVariable Long storeId, @PathVariable Long menuId,
                                                          @AuthenticationPrincipal MyUserDetail myUserDetail) {
        return ResponseEntity.ok(menuService.findModifyMenuService(menuChangeStauts, storeId, menuId, myUserDetail.getUsername()));
    }

    /**
     * 메뉴 삭제
     * @param menuid
     * @param myUserDetail
     * @return
     */

    @DeleteMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long menuId, @PathVariable Long storeId,
                                           @AuthenticationPrincipal MyUserDetail myUserDetail) {
        menuService.deleteMenuService(menuId, storeId, myUserDetail.getUsername());
        return ResponseEntity.ok("메뉴가 삭제 완료되었습니다!");
    }
}
