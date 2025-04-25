package com.example.minzok.store.service;

import com.example.minzok.store.dto.StoreMemberDto;
import com.example.minzok.store.dto.StoreModifyDto;
import com.example.minzok.store.dto.StoreRequestDto;
import com.example.minzok.store.dto.StoreResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface StoreService {

    StoreMemberDto createStoreService(StoreRequestDto storeRequestDto, String email);

    StoreResponseDto patchStore(StoreModifyDto storeModifyDto, Long storeId, String email);

    void deleteStoreService(Long storeId, String email);

    Slice<StoreResponseDto> findStorePage(String keyword, Pageable pageable);

    Slice<StoreResponseDto> findOneStore(Long storeId, Pageable pageable);

}
