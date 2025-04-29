package com.example.minzok.order.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

// 주문 상세 응답
@Getter
@Builder
public class OrderDetailResponseDto {
    private Long orderId;
    private Long storeId;
    private Long totalPrice;
    private String orderStatus;
    private LocalDateTime orderTime;
    private List<MenuDetailDto> menus;

    @Getter
    @Builder
    public static class MenuDetailDto {
        private String menuName;
        private int quantity;
        private Long price;
    }
}
