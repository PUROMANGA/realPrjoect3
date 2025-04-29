package com.example.minzok.store.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor

public class StoreRequestDto {

    @NotBlank(message = "내용을 입력해주세요")
    private String storeName;

    @NotBlank(message = "내용을 입력해주세요")
    private String storeContent;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime openTime;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime closeTime;

    @Min(value = 1, message = "최소 주문 금액은 1 이상이어야 합니다")
    private int minimumOrderAmount;

    public StoreRequestDto(String storeName, String storeContent, LocalTime openTime, LocalTime closeTime, int minimumOrderAmount) {
        this.storeName = storeName;
        this.storeContent = storeContent;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumOrderAmount = minimumOrderAmount;
    }
}
