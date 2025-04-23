package com.example.minzok.store.controller;


import com.example.minzok.store.dto.StoreRequestDto;
import com.example.minzok.store.dto.StoreResponseDto;
import com.example.minzok.store.service.StoreServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
@Slf4j

public class StoreController {

    private final StoreServiceImpl storeService;

    /**
     * 입력받은 memberid로 member을 찾아주고, member와 입력받은 storeRequestDto로 Store를 저장합니다.
     * 그 후 StoreResponseDto로 반환합니다.
     * @param storeRequestDto
     * @param memberId
     * @return
     */

    @PostMapping
    public ResponseEntity<StoreResponseDto> createStore(@RequestBody StoreRequestDto storeRequestDto, Long memberId) {
        StoreResponseDto createStore = storeService.createStoreService(storeRequestDto, memberId);
        return ResponseEntity.ok(createStore);
    }

    @PatchMapping
    public ResponseEntity<StoreResponseDto> patchStore(@RequestBody StoreRequestDto storeRequestDto, @PathVariable Long StoreId, Long memberId) {
        storeService.patchStore(storeRequestDto,StoreId, memberId);

    }


}
