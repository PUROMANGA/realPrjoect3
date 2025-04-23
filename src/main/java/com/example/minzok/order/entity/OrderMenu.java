package com.example.minzok.order.entity;

import com.example.minzok.menu.Entity.Menu;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_menus")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 주문
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    // 메뉴
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    // 주문한 메뉴의 수량
    private int quantity;

    // 연관관계 편의 메서드
    public void setOrder(Order order) {
        this.order = order;
    }
}
