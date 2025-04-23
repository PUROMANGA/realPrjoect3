package com.example.minzok.order.dto.request;

import lombok.Getter;

// 주문 생성 요청
@Getter
public class OrderRequestDto {
    private Long storeId;
    private Long menuId;
    private int quantity;
}

