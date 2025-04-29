package com.example.minzok.order.entity;

// 주문 상태를 enum으로 받는다.
public enum OrderStatus {

    /**
     WAITING: 대기 상태,
     CONFIRM: 수락,
     COOKING: 조리 중,
     DELIVERING: 배달 중,
     COMPLETED: 완료.
     */

    WAITING, CONFIRM, COOKING, DELIVERING, COMPLETED
}
