package com.example.minzok.order.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// 주문 상세 응답
@Getter
@Builder
public class OrderDetailResponseDto {
    private Long orderId;
    private Long storeId;
    private String menuName;
    private int quantity;
    private int totalPrice;
    private String orderStatus;
    private LocalDateTime orderTime;
}
