package com.example.minzok.store.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor

public class StoreRequestDto {

    @NotBlank(message = "내용을 입력해주세요")
    private String Store_name;

    @NotBlank(message = "내용을 입력해주세요")
    private String Store_content;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime openTime;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime closeTime;

    @Min(value = 1, message = "최소 주문 금액은 1 이상이어야 합니다")
    private int Minimum_order_amount;

    public StoreRequestDto(String store_name, String store_content, LocalTime openTime, LocalTime closeTime, int minimum_order_amount) {
        this.Store_name = store_name;
        this.Store_content = store_content;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.Minimum_order_amount = minimum_order_amount;
    }
}
