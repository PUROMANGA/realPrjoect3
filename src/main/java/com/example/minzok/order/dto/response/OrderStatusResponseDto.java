package com.example.minzok.order.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// 주문 상태 변경 응답
@Getter
@Builder
public class OrderStatusResponseDto {
    private Long orderId;
    private Long storeId;
    private String orderStatus;
    private LocalDateTime statusChangedTime;
}
