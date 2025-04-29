package com.example.minzok.member.dto.response;

import com.example.minzok.member.entity.Member;
import com.example.minzok.order.entity.OrderStatus;
import com.example.minzok.store.entity.Store;
import lombok.Getter;

@Getter
public class MemberStoreOrderCountDto {

    private String storeName;

    private int orderCount;

    public MemberStoreOrderCountDto() {
    }

    public MemberStoreOrderCountDto(String storeName, int orderCount) {
        this.storeName = storeName;
        this.orderCount = orderCount;
    }

}
