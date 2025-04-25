package com.example.minzok.store.common;


import com.example.minzok.store.entity.Store;
import com.example.minzok.store.entity.StoreStatus;
import com.example.minzok.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor

public class Common {

    private final StoreRepository storeRepository;

    @Scheduled(cron = "0 */10 * * * *")
    public void updateWithDrawStatus() {

        List<StoreStatus> statusList = List.of(StoreStatus.OPEN, StoreStatus.CLOSED);

        List<Store> stores = storeRepository.findStatusIn(statusList);
        LocalTime now = LocalTime.now();

        for(Store store : stores) {
            if(now.isBefore(store.getOpenTime()) || now.isAfter(store.getCloseTime())) {
                store.setStoreStatus(StoreStatus.CLOSED);
            } else {
                store.setStoreStatus(StoreStatus.OPEN);
            }
        }

        storeRepository.saveAll(stores);
    }
}
