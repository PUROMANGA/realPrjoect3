package com.example.minzok.store.dto;

import lombok.Getter;

import java.time.LocalTime;

@Getter

public class StoreMenuDto {

    private Long storeId;
    private String store_name;
    private String menuName;
    private String Store_content;
    private LocalTime openTime;
    private LocalTime closeTime;
    private int Minimum_order_amount;

    public StoreMenuDto(Long storeId, String store_name, String menuName, String store_content, LocalTime openTime, LocalTime closeTime, int minimum_order_amount) {
        this.storeId = storeId;
        this.store_name = store_name;
        this.menuName = menuName;
        this.Store_content = store_content;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.Minimum_order_amount = minimum_order_amount;
    }
}
