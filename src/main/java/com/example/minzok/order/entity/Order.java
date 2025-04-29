package com.example.minzok.order.entity;

import com.example.minzok.global.base_entity.BaseEntity;
import com.example.minzok.member.entity.Member;
import com.example.minzok.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // order는 예약어라서 orders로 설정.
@Getter
@NoArgsConstructor
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    // order-menus 중간 테이블과 1:N 관계
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    private Long totalPrice;
    private LocalDateTime orderTime;
    private LocalDateTime statusChangedTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public Order(Member member, Store store){
        this.member = member;
        this.store = store;
        this.orderMenus = new ArrayList<>();
        this.orderTime = LocalDateTime.now();
        this.statusChangedTime = this.orderTime; // 처음 주문 시 동일하다.
        this.orderStatus = OrderStatus.WAITING;  // 필수로 WAITING으로 초기화를 해줘야 한다.
        calculateTotalPrice();
    }

    // 총 금액 계산 로직 추가
    public void calculateTotalPrice() {
        this.totalPrice = (long) orderMenus.stream()
                .mapToLong(om ->
                        om.getMenu().getPrice() * om.getQuantity())
                .sum();
    }

    public void addOrderMenu(OrderMenu orderMenu){
        this.orderMenus.add(orderMenu);
        orderMenu.setOrder(this); // 양방향 연관관계 설정.
        calculateTotalPrice();
    }

    public void changeStatus(OrderStatus orderStatus){
        this.orderStatus = orderStatus;
        this.statusChangedTime = LocalDateTime.now();
    }

}
