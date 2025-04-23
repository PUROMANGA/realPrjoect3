package com.example.minzok.store.controller;


import com.example.minzok.member.repository.MemberRepository;
import com.example.minzok.store.dto.StoreRequestDto;
import com.example.minzok.store.dto.StoreResponseDto;
import com.example.minzok.store.service.StoreServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.example.minzok.global.auth.MyUserDetail;

@RestController
@RequestMapping("/store")
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
    public ResponseEntity<StoreResponseDto> createStore(@RequestBody StoreRequestDto storeRequestDto, @AuthenticationPrincipal MyUserDetail myUserDetail) {
        StoreResponseDto createStore = storeService.createStoreService(storeRequestDto, myUserDetail.getUsername());
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
    public ResponseEntity<StoreResponseDto> patchStore(@RequestBody StoreRequestDto storeRequestDto,
                                                       @PathVariable Long storeId,
                                                       @AuthenticationPrincipal MyUserDetail myUserDetail) {
        return ResponseEntity.ok(storeService.patchStore(storeRequestDto,storeId, myUserDetail.getUsername()));
    }



}
