package com.example.minzok.menu.Dto.Request;

import com.example.minzok.menu.Entity.MenuStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public class MenuChangeStauts {

    @NotBlank(message = "내용을 기입해주세요")
    private String name;

    @NotNull
    private Long price;

    @NotBlank(message = "내용을 기입해주세요")
    private String description;

    private MenuStatus menuStatus;
}
