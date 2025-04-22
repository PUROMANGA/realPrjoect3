package com.example.minzok.store.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data

public class StoreResponseDto {

    private Long id;
    private String Store_name;
    private String Store_content;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
    private int Minimum_order_amount;
    private LocalDateTime creatTime;
    private LocalDateTime modifiedTime;
}
