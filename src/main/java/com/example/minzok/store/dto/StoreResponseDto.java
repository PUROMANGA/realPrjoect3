package com.example.minzok.store.dto;

import com.example.minzok.member.entity.Member;
import com.example.minzok.store.entity.Store;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data

public class StoreResponseDto {
    private Long id;
    private String Store_name;
    private String Store_content;
    private LocalTime openTime;
    private LocalTime closeTime;
    private int Minimum_order_amount;
    private LocalDateTime creatTime;
    private LocalDateTime modifiedTime;
    private boolean withdrawn;

    public StoreResponseDto(Store store) {
        this.id = store.getId();
        this.Store_name = store.getStore_name();
        this.Store_content = store.getStore_content();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.Minimum_order_amount = store.getMinimum_order_amount();
        this.creatTime = store.getCreatTime();
        this.modifiedTime = store.getModifiedTime();
    }
}
