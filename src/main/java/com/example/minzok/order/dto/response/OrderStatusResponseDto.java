package com.example.minzok.order.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderStatusResponseDto {
    private Long orderId;
    private String orderStatus;
    private LocalDateTime statusChangedTime;
}
