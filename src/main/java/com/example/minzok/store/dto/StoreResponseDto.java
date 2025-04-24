package com.example.minzok.store.dto;

import com.example.minzok.menu.Entity.Menu;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.entity.StoreStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data

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
    private int countStore;

    public StoreResponseDto(Store store) {
        this.id = store.getId();
        this.storeName = store.getStoreName();
        this.storeContent = store.getStoreContent();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.minimumOrderAmount = store.getMinimumOrderAmount();
        this.creatTime = store.getCreatTime();
        this.modifiedTime = store.getModifiedTime();
    }

    public StoreResponseDto(Store store, Menu menu) {
        this.id = store.getId();
        this.storeName = store.getStoreName();
        this.storeContent = store.getStoreContent();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.minimumOrderAmount = store.getMinimumOrderAmount();
        this.creatTime = store.getCreatTime();
        this.modifiedTime = store.getModifiedTime();
        this.storeStatus = store.getStoreStatus();
        this.menuName = menu.getName();
    }
}
