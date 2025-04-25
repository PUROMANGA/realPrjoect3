package com.example.minzok.store.controller;


import com.example.minzok.member.repository.MemberRepository;
import com.example.minzok.store.dto.*;
import com.example.minzok.store.service.StoreServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.minzok.auth.entity.MyUserDetail;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
@Slf4j

public class StoreController {

    private final StoreServiceImpl storeService;
    private final MemberRepository memberRepository;

    /**
     * 입력받은 memberid로 member을 찾아주고, member와 입력받은 storeRequestDto로 Store를 저장합니다.
     * 그 후 StoreResponseDto로 반환합니다.
     * @param storeRequestDto
     * @param memberId
     * @return
     */

    @PostMapping
    public ResponseEntity<StoreMemberDto> createStore(@RequestBody @Validated StoreRequestDto storeRequestDto,
                                                      @AuthenticationPrincipal MyUserDetail myUserDetail) {
        StoreMemberDto createStore = storeService.createStoreService(storeRequestDto, myUserDetail.getUsername());
        return ResponseEntity.ok(createStore);
    }

    /**
     * 입력받은 sotreId의 유무를 확인하고, 있다면 수정해줍니다.
     * @param storeRequestDto
     * @param storeId
     * @param myUserDetail
     * @return
     */

    @PatchMapping ("/{storeId}")
    public ResponseEntity<StoreResponseDto> patchStore(@RequestBody @Validated StoreModifyDto storeModifyDto,
                                                       @PathVariable Long storeId,
                                                       @AuthenticationPrincipal MyUserDetail myUserDetail) {
        return ResponseEntity.ok(storeService.patchStore(storeModifyDto, storeId, myUserDetail.getUsername()));
    }

    /**
     * 입력받은 storeId의 유무를 확인하고, 있다면 삭제합니다.
     * @param storeId
     * @param myUserDetail
     * @return
     */

    @DeleteMapping("/{storeId}")
    public ResponseEntity<String> deleteStore(@PathVariable Long storeId,
                                                        @AuthenticationPrincipal MyUserDetail myUserDetail) {
        storeService.deleteStoreService(storeId, myUserDetail.getUsername());
        return ResponseEntity.ok("가게 삭제가 완료되었습니다.");
    }

    /**
     * 특정 KeyWord가 들어간 메뉴의 이름을 가지고 있는 가게를 조회
     * @param keyword
     * @param pageable
     * @return
     */

    @GetMapping
    public ResponseEntity<Slice<OnlyStoreResponseDto>> findStorePage
            (@RequestParam String keyword,
             @PageableDefault(size = 10, sort = "creatTime", direction = DESC) Pageable pageable) {
        return ResponseEntity.ok(storeService.findStorePage(keyword, pageable));
    }

    /**
     * 특정 가게를 조회하면 모든 메뉴가 같이 나옴
     * @param storeId
     * @return
     */

    @GetMapping("/{storeId}")
    public ResponseEntity<Slice<StoreResponseDto>> findOneStore
    (@PathVariable Long storeId,
     @PageableDefault(size = 10, sort = "creatTime", direction = DESC)Pageable pageable) {
        return ResponseEntity.ok(storeService.findOneStore(storeId, pageable));
    }
}
