package com.example.minzok.order.dto.request;

import lombok.Getter;

// 주문 상태 변경 요청
@Getter
public class OrderStatusRequestDto {
    private final String orderStatus;

    public OrderStatusRequestDto(String orderStatus){
        this.orderStatus = orderStatus;
    }
}
