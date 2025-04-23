package com.example.minzok.store.service;

import com.example.minzok.store.dto.StoreRequestDto;
import com.example.minzok.store.dto.StoreResponseDto;

public interface StoreService {

    public StoreResponseDto createStoreService(StoreRequestDto storeRequestDto, String email);

    public StoreResponseDto patchStore(StoreRequestDto storeRequestDto, Long storeId, String email);
}
