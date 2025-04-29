package com.example.minzok.store.repository;


import com.example.minzok.store.dto.StoreResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CustomStoreRepository {

    Slice<StoreResponseDto> menuFindById(Long storeId, Pageable pageable);
}
