package com.example.minzok.store.controller;


import com.example.minzok.store.dto.StoreRequestDto;
import com.example.minzok.store.dto.StoreResponseDto;
import com.example.minzok.store.service.StoreServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
@Slf4j

public class StoreController {

    private final StoreServiceImpl storeService;

    @PostMapping
    public EntityResponse<StoreResponseDto> createStore(@RequestBody StoreRequestDto storeRequestDto, Long memberId) {
        storeService.createStoreService(storeRequestDto, memberId);

    }
}
