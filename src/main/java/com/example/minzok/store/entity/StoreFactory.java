package com.example.minzok.store.entity;

import com.example.minzok.member.entity.Member;
import com.example.minzok.store.dto.StoreRequestDto;

public class StoreFactory {

    public static Store storeFactory(StoreRequestDto storeRequestDto, Member member) {
        return new Store(storeRequestDto.getStore_name(),
                storeRequestDto.getStore_content(),
                storeRequestDto.getOpenTime(),
                storeRequestDto.getCloseTime(),
                storeRequestDto.getMinimum_order_amount(),
                member.getId()
        );
    }

    public static Store update(StoreRequestDto storeRequestDto) {
        return new Store(storeRequestDto.getStore_name(),
                storeRequestDto.getStore_content(),
                storeRequestDto.getOpenTime(),
                storeRequestDto.getCloseTime(),
                storeRequestDto.getMinimum_order_amount());

    }
}
