package com.example.minzok.menu.Dto.Response;

import com.example.minzok.menu.Entity.Menu;
import com.example.minzok.menu.Entity.MenuStatus;
import com.example.minzok.store.entity.Store;
import lombok.Getter;

@Getter

public class MenuResponseDto {

    private Long id;
    private String name;
    private Long price;
    private String description;
    private String storeName;
    private MenuStatus menuStatus;


    public MenuResponseDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.description = menu.getDescription();
        this.storeName = menu.getStore().getStoreName();
        this.menuStatus = menu.getMenuStatus();
    }
}
