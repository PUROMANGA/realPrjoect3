package com.example.minzok.order.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// 주문 생성 응답
@Getter @Builder
public class OrderResponseDto {
    private Long orderId;
    private Long storeId;
    private int totalPrice;
    private String status;
    private LocalDateTime orderTime;
}
