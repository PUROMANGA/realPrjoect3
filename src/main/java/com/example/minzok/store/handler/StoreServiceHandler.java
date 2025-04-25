package com.example.minzok.store.handler;

import com.example.minzok.global.error.CustomNullPointerException;
import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class StoreServiceHandler {

    private final StoreRepository storeRepository;

    public Store foundStoreAndException(Long storeId, String email){
        Store foundStore = storeRepository.findById(storeId).orElseThrow(() -> new CustomNullPointerException(ExceptionCode.CANT_FIND_STORE));

        if(!foundStore.getMember().getEmail().equals(email)) {
            throw new CustomRuntimeException(ExceptionCode.NO_EDIT_PERMISSION);
        }

        return foundStore;
    }
}
