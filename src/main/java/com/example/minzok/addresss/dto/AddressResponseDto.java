package com.example.minzok.addresss.dto;

public class AddressResponseDto {

    private String email;

    private String fullAddress;

    public AddressResponseDto(String email, String fullAddress) {
        this.email = email;
        this.fullAddress = fullAddress;
    }
}
