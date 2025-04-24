package com.example.minzok.addresss.service;

import com.example.minzok.addresss.dto.AddressRequestDto;
import com.example.minzok.addresss.dto.AddressResponseDto;
import com.example.minzok.auth.entity.MyUserDetail;

import java.util.List;

public interface AddressService {

    AddressResponseDto createAddress(AddressRequestDto dto, MyUserDetail myUserDetail);
    List<AddressResponseDto> findAddressByMember(MyUserDetail myUserDetail);
    List<AddressResponseDto> updateAddressType(Long id, MyUserDetail myUserDetail);
    void deleteAddress(Long id, MyUserDetail myUserDetail);
    boolean matchMember (Long id, String email);


}
