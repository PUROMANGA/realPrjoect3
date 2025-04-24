package com.example.minzok.store.repository;

import com.example.minzok.menu.Entity.QMenu;
import com.example.minzok.store.dto.StoreResponseDto;
import com.example.minzok.store.entity.QStore;
import com.example.minzok.store.entity.StoreStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

@RequiredArgsConstructor

public class StoreRepositoryImpl implements CustomStoreRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<StoreResponseDto> menuFindById(Long storeId, Pageable pageable) {

        QStore store = QStore.store;
        QMenu menu = QMenu.menu;

        List<StoreResponseDto> content = jpaQueryFactory
                .select(Projections.constructor(StoreResponseDto.class,
                        store.id,
                        store.storeName,
                        store.storeContent,
                        store.openTime,
                        store.closeTime,
                        store.minimumOrderAmount,
                        store.creatTime,
                        store.modifiedTime,
                        store.storeStatus,
                        menu.name
                ))
                .from(store)
                .leftJoin(store.menus, menu)
                .where(store.id.eq(storeId)
                        .and(store.storeStatus.eq(StoreStatus.OPEN))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = content.size() > pageable.getPageSize();
        if (hasNext) content.remove(pageable.getPageSize());

        return new SliceImpl<>(content, pageable, hasNext);
    }
}
