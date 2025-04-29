package com.example.minzok.store.dto;

import com.example.minzok.store.entity.Store;
import com.example.minzok.store.entity.StoreStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter

public class StoreResponseDto {
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

    public StoreResponseDto(Store store) {
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

    @QueryProjection
    public StoreResponseDto(Long id, String storeName, String storeContent,
                            LocalTime openTime, LocalTime closeTime, int minimumOrderAmount,
                            LocalDateTime creatTime, LocalDateTime modifiedTime,
                            StoreStatus storeStatus, String menuName) {
        this.id = id;
        this.storeName = storeName;
        this.storeContent = storeContent;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumOrderAmount = minimumOrderAmount;
        this.creatTime = creatTime;
        this.modifiedTime = modifiedTime;
        this.storeStatus = storeStatus;
        this.menuName = menuName;
    }

}
