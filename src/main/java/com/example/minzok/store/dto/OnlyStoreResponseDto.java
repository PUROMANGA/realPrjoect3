package com.example.minzok.store.dto;

import com.example.minzok.store.entity.Store;
import com.example.minzok.store.entity.StoreStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter

public class OnlyStoreResponseDto {
    private Long id;
    private String storeName;
    private String storeContent;
    private LocalTime openTime;
    private LocalTime closeTime;
    private int minimumOrderAmount;
    private LocalDateTime creatTime;
    private LocalDateTime modifiedTime;
    private StoreStatus storeStatus;

    public OnlyStoreResponseDto(Store store) {
        this.id = store.getId();
        this.storeName = store.getStoreName();
        this.storeContent = store.getStoreContent();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.minimumOrderAmount = store.getMinimumOrderAmount();
        this.storeStatus = store.getStoreStatus();
        this.creatTime = store.getCreatTime();
        this.modifiedTime = store.getModifiedTime();
    }
}
