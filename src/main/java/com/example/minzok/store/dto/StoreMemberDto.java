package com.example.minzok.store.dto;

import com.example.minzok.member.entity.Member;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.entity.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class StoreMemberDto {

    /**
     * Store와 Member의 요소를 표현하기 위해 만들어준 Dto입니다
     */

    private Long id;
    private String storeName;
    private String storeContent;
    private LocalTime openTime;
    private LocalTime closeTime;
    private int minimumOrderAmount;
    private LocalDateTime creatTime;
    private LocalDateTime modifiedTime;
    private StoreStatus storeStatus;
    private String menuName;
    private int countStore;

    public StoreMemberDto(Store store, Member member) {
        this.id = store.getId();
        this.storeName = store.getStoreName();
        this.storeContent = store.getStoreContent();
        this.storeStatus = store.getStoreStatus();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.minimumOrderAmount = store.getMinimumOrderAmount();
        this.creatTime = store.getCreatTime();
        this.modifiedTime = store.getModifiedTime();
        this.countStore = member.getStoreCount();
    }
}
