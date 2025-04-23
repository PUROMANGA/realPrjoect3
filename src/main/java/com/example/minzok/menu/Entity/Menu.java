package com.example.minzok.menu.Entity;

import com.example.minzok.global.base_entity.BaseEntity;
import com.example.minzok.order.entity.OrderMenu;
import com.example.minzok.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    /**
     * 주문 - 메뉴 중간 테이블과의 관계
     */

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenu> orderMenu = new ArrayList<>();

    public Menu(String name , Long price, String description, Store store) {
            this.name = name;
            this.price = price;
            this.description = description;
            this.store = store;
    }
}
