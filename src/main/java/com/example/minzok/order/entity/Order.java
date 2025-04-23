package com.example.minzok.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "store_id")
//    private Store store;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "menu_id")
//    private Menu menu;

    private int totalPrice;
    private int quantity;

    private LocalDateTime orderTime;
    private LocalDateTime statusChangedTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    // Long memberId, Long storeId, Long menuId 추가 해야 됨.
    public Order(int totalPrice, int quantity){
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.orderTime = LocalDateTime.now();
        this.statusChangedTime = this.orderTime; // 처음 주문 시 동일하다.
        this.orderStatus = OrderStatus.WAITING;
    }
}
