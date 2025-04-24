package com.example.minzok.addresss.controller;

import com.example.minzok.addresss.dto.AddressRequestDto;
import com.example.minzok.addresss.dto.AddressResponseDto;
import com.example.minzok.addresss.service.AddressService;
import com.example.minzok.global.jwt.MyUserDetail;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressResponseDto> createAddress (
            @Valid @RequestBody AddressRequestDto dto,
            @AuthenticationPrincipal MyUserDetail myUserDetail
    ) {
        return new ResponseEntity<>(addressService.createAddress(dto, myUserDetail), HttpStatus.CREATED);
    }




}
