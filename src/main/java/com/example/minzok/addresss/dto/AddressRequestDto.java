package com.example.minzok.addresss.dto;

import com.example.minzok.addresss.enums.AddressType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class AddressRequestDto {

    private String lotNumberAddress;

    private String detailAddress;

    public AddressRequestDto(String lotNumberAddress, String detailAddress) {
        this.lotNumberAddress = lotNumberAddress;
        this.detailAddress = detailAddress;
    }

    public AddressRequestDto() {
    }
}
