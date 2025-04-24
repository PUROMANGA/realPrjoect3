package com.example.minzok.store.entity;

import com.example.minzok.member.entity.Member;
import com.example.minzok.store.dto.StoreRequestDto;

public class StoreFactory {

    public static Store storeFactory(StoreRequestDto storeRequestDto, Member member) {
        return new Store(storeRequestDto.getStoreName(),
                storeRequestDto.getStoreContent(),
                storeRequestDto.getOpenTime(),
                storeRequestDto.getCloseTime(),
                storeRequestDto.getMinimumOrderAmount(),
                member
        );
    }
}
