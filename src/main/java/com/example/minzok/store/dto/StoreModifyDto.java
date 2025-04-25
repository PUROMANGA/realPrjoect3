package com.example.minzok.store.dto;

import com.example.minzok.store.entity.StoreStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class StoreModifyDto {

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

    private StoreStatus storeStatus;
}
