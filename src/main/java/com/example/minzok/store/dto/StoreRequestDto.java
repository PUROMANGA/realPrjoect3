package com.example.minzok.store.dto;

import lombok.Data;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;

@Data

public class StoreRequestDto {

    @NotBlank(message = "내용을 입력해주세요")
    private String Store_name;

    @NotBlank(message = "내용을 입력해주세요")
    private String Store_content;

    @NotBlank(message = "내용을 입력해주세요")
    private LocalDateTime openTime;

    @NotBlank(message = "내용을 입력해주세요")
    private LocalDateTime closeTime;

    @NotBlank(message = "내용을 입력해주세요")
    private int Minimum_order_amount;
}
