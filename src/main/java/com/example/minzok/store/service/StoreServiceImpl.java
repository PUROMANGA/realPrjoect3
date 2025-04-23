package com.example.minzok.store.service;

import com.example.minzok.store.dto.StoreRequestDto;
import com.example.minzok.store.dto.StoreResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor

public class StoreServiceImpl implements StoreService {

    @Override
    public StoreResponseDto createStoreService(StoreRequestDto storeRequestDto, Long memberId) {

    }
}
