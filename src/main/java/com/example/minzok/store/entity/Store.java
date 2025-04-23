package com.example.minzok.store.entity;

import com.example.minzok.global.base_entity.BaseEntity;
import com.example.minzok.member.entity.Member;
import com.example.minzok.store.dto.StoreRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor

public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String Store_name;

    @Column(nullable = false)
    private String Store_content;

    @Column(nullable = false)
    private LocalTime openTime;

    @Column(nullable = false)
    private LocalTime closeTime;

    private int Minimum_order_amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StoreStatus storeStatus;

    public Store(String store_name, String store_content, LocalTime openTime, LocalTime closeTime, int minimum_order_amount, Long id) {
        this.Store_name = store_name;
        this.Store_content = store_content;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.Minimum_order_amount = minimum_order_amount;
    }

    public void update(StoreRequestDto storeRequestDto) {
        this.Store_name = storeRequestDto.getStore_name(),
        this.Store_content = storeRequestDto.getStore_content(),
        this.openTime = storeRequestDto.getOpenTime(),
        this.closeTime = storeRequestDto.getCloseTime(),
        this.Minimum_order_amount = storeRequestDto.getMinimum_order_amount());
    }
}
