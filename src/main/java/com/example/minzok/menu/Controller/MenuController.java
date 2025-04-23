package com.example.minzok.menu.Controller;


import com.example.minzok.menu.Service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class MenuController {

    private final MenuService menuService;
}
