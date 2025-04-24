package com.example.minzok.order.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

// 주문 생성 응답
@Getter @Builder
public class OrderResponseDto {
    private Long orderId;
    private Long storeId;
    private int totalPrice;
    private String status;
    private LocalDateTime orderTime;
    private List<OrderMResponseDto> menus;

    @Getter
    @Builder
    public static class OrderMResponseDto{
        private String menuName;
        private int quantity;
        private long price;
    }
}
