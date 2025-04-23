package com.example.minzok.store.service;

import com.example.minzok.addresss.entity.Address;
import com.example.minzok.addresss.service.AddressService;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.entity.StoreFactory;
import com.example.minzok.store.dto.StoreRequestDto;
import com.example.minzok.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final AddressService addressService;

    @Transactional
    public Store createStore(StoreRequestDto storeRequestDto) {
        Address address = addressService.createAddress(storeRequestDto.getAddress());
        Store store = StoreFactory.createStoreWithAddress(storeRequestDto, address);
        return storeRepository.save(store);
    }
}
