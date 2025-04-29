package com.example.minzok.store.service;

import com.example.minzok.store.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface StoreService {

    StoreMemberDto createStoreService(StoreRequestDto storeRequestDto, String email);

    StoreResponseDto patchStore(StoreModifyDto storeModifyDto, Long storeId, String email);

    void deleteStoreService(Long storeId, String email);

    Slice<OnlyStoreResponseDto> findStorePage(String keyword, Pageable pageable);

    Slice<StoreResponseDto> findOneStore(Long storeId, Pageable pageable);

}
