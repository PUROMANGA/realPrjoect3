package com.example.minzok.menu.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter

public class MenuRequestDto {

    @NotBlank(message = "내용을 기입해주세요")
    private String name;

    private Long price;

    @NotBlank(message = "내용을 기입해주세요")
    private String description;
}
