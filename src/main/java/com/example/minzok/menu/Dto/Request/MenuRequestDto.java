package com.example.minzok.menu.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class MenuRequestDto {

    @NotBlank(message = "내용을 기입해주세요")
    private String name;

    @NotNull
    private Long price;

    @NotBlank(message = "내용을 기입해주세요")
    private String description;

    public MenuRequestDto(String name, Long price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
