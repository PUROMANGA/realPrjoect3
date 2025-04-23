package com.example.minzok.order.entity;

import com.example.minzok.global.base_entity.BaseEntity;
import com.example.minzok.member.entity.Member;
import com.example.minzok.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "orders") // order는 예약어라서 orders로 설정.
@NoArgsConstructor
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

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
