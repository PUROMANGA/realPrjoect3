package com.example.minzok.menu.Entity;

import com.example.minzok.global.base_entity.BaseEntity;
import com.example.minzok.member.entity.Member;
import com.example.minzok.menu.Dto.Request.MenuChangeStauts;
import com.example.minzok.menu.Dto.Request.MenuRequestDto;
import com.example.minzok.order.entity.OrderMenu;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.entity.StoreStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "menus")

public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MenuStatus menuStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;
    
    public Menu(MenuRequestDto menuRequestDto, Store store) {
        this.name = menuRequestDto.getName();
        this.price = menuRequestDto.getPrice();
        this.description = menuRequestDto.getDescription();
        this.store = store;
    }

    public void update(MenuChangeStauts menuChangeStauts) {
        this.name = menuChangeStauts.getName();
        this.price = menuChangeStauts.getPrice();
        this.description = menuChangeStauts.getDescription();
        this.menuStatus = menuChangeStauts.getMenuStatus();
    }

    public Menu(String name, Long price, String description, Store store) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.store = store;
    }
}
