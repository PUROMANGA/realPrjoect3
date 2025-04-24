package com.example.minzok.addresss.dto;

import com.example.minzok.addresss.entity.Address;
import lombok.Getter;

@Getter
public class AddressResponseDto {

    private final Long id;

    private final String email;

    private final String fullAddress;

    private final String addressType;

    public AddressResponseDto(Long id, String email, String fullAddress, String addressType) {
        this.id = id;
        this.email = email;
        this.fullAddress = fullAddress;
        this.addressType =addressType;
    }

    public static AddressResponseDto toDto (Address address){
        return new AddressResponseDto(
                address.getId(),
                address.getMember().getEmail(),
                address.getAddressInfo(),
                address.getAddressType().toString()
                );
    }
}
