package com.example.minzok.addresss.controller;

import com.example.minzok.addresss.dto.AddressRequestDto;
import com.example.minzok.addresss.dto.AddressResponseDto;
import com.example.minzok.addresss.service.AddressService;
import com.example.minzok.global.jwt.MyUserDetail;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping
    public ResponseEntity<List<AddressResponseDto>> findAddress(@AuthenticationPrincipal MyUserDetail myUserDetail)
    {
        return new ResponseEntity<>(addressService.findAddressByMember(myUserDetail), HttpStatus.OK);
    }

    @PreAuthorize("@addressServiceImpl.matchMember(#addressId, principal.username)")
    @PatchMapping("/{addressId}")
    public ResponseEntity<List<AddressResponseDto>> updateAddressType(@PathVariable Long addressId, @AuthenticationPrincipal MyUserDetail myUserDetail){
        return new ResponseEntity<>(addressService.updateAddressType(addressId, myUserDetail), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or @addressServiceImpl.matchMember(#addressId, principal.username)")
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Map<String, String>> deleteAddress(@PathVariable Long addressId){
        addressService.deleteAddress(addressId);
        return new ResponseEntity<>(Map.of("message", "선택하신 주소가 삭제되었습니다."),HttpStatus.OK);
    }
}
