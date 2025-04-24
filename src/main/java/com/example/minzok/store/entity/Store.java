package com.example.minzok.store.entity;

import com.example.minzok.global.base_entity.BaseEntity;
import com.example.minzok.member.entity.Member;
import com.example.minzok.menu.Entity.Menu;
import com.example.minzok.store.dto.StoreRequestDto;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor

public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String storeName;

    @Column(nullable = false)
    private String storeContent;

    @Column(nullable = false)
    private LocalTime openTime;

    @Column(nullable = false)
    private LocalTime closeTime;

    private int minimumOrderAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StoreStatus storeStatus;

    @OneToMany(mappedBy = "store")
    private List<Menu> menus = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "members_id")
    private Member member;

    public Store(Long id, String storeName, String storeContent, LocalTime openTime, LocalTime closeTime, int minimumOrderAmount, StoreStatus storeStatus, Member member) {
        this.id = id;
        this.storeName = storeName;
        this.storeContent = storeContent;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumOrderAmount = minimumOrderAmount;
        this.storeStatus = storeStatus;
        this.member = member;
    }

    public Store(String storeName, String storeContent, LocalTime openTime, LocalTime closeTime, int minimumOrderAmount, Member member) {
        this.storeName = storeName;
        this.storeContent = storeContent;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumOrderAmount = minimumOrderAmount;
        this.member = member;
    }

    public Store(String storeName, String storeContent, LocalTime openTime, LocalTime closeTime, int minimumOrderAmount, StoreStatus storeStatus) {
        this.storeName = storeName;
        this.storeContent = storeContent;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumOrderAmount = minimumOrderAmount;
        this.storeStatus = storeStatus;
    }

    public void update(StoreRequestDto storeRequestDto) {
        this.storeName = storeRequestDto.getStoreName();
        this.storeContent = storeRequestDto.getStoreContent();
        this.openTime = storeRequestDto.getOpenTime();
        this.closeTime = storeRequestDto.getCloseTime();
        this.minimumOrderAmount = storeRequestDto.getMinimumOrderAmount();
    }
}
