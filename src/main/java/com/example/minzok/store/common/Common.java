package com.example.minzok.store.common;


import com.example.minzok.store.entity.Store;
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

    @Scheduled(cron = "0 /10 * * * *")
    public void updateWithDrawStatus() {

        List<Store> stores = storeRepository.findByWithdrawnIsFalse();
        LocalTime now = LocalTime.now();

        for(Store store : stores) {
            boolean isClosed = now.isBefore(store.getOpenTime()) || now.isAfter(store.getCloseTime());
            store.setWithdrawn(isClosed);
        }

        storeRepository.saveAll(stores);
    }
}
