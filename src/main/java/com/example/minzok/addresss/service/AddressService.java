package com.example.minzok.addresss.service;

import com.example.minzok.addresss.dto.AddressRequestDto;
import com.example.minzok.addresss.dto.AddressResponseDto;
import com.example.minzok.global.jwt.MyUserDetail;
import org.springframework.transaction.annotation.Transactional;

public interface AddressService {

    AddressResponseDto createAddress(AddressRequestDto dto, MyUserDetail myUserDetail);

}
