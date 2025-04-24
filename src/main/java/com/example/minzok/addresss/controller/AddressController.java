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

    /**
     * 로그인한 유저 기반 주소 저장
     * @param dto
     * @param myUserDetail
     * @return
     */
    @PostMapping
    public ResponseEntity<AddressResponseDto> createAddress (
            @Valid @RequestBody AddressRequestDto dto,
            @AuthenticationPrincipal MyUserDetail myUserDetail
    ) {
        return new ResponseEntity<>(addressService.createAddress(dto, myUserDetail), HttpStatus.CREATED);
    }

    /**
     * 주소 리스트 조회
     * @param myUserDetail
     * @return
     */
    @GetMapping
    public ResponseEntity<List<AddressResponseDto>> findAddress(@AuthenticationPrincipal MyUserDetail myUserDetail)
    {
        return new ResponseEntity<>(addressService.findAddressByMember(myUserDetail), HttpStatus.OK);
    }

    /**
     * 자기 자신의 주소만 가능
     * 지정된 주소를 대표주소로 변경 나머지는 일반주소로 변경
     * @param addressId
     * @param myUserDetail
     * @return
     */
    @PreAuthorize("@addressServiceImpl.matchMember(#addressId, principal.username)")
    @PatchMapping("/{addressId}")
    public ResponseEntity<List<AddressResponseDto>> updateAddressType(@PathVariable Long addressId, @AuthenticationPrincipal MyUserDetail myUserDetail){
        return new ResponseEntity<>(addressService.updateAddressType(addressId, myUserDetail), HttpStatus.OK);
    }

    /**
     * 자기 자신 혹은 관리자만 가능
     * 지정된 주소 삭제
     * @param addressId
     * @param myUserDetail
     * @return
     */
    @PreAuthorize("hasRole('ADMIN') or @addressServiceImpl.matchMember(#addressId, principal.username)")
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Map<String, String>> deleteAddress(@PathVariable Long addressId, @AuthenticationPrincipal MyUserDetail myUserDetail){
        addressService.deleteAddress(addressId, myUserDetail);
        return new ResponseEntity<>(Map.of("message", "선택하신 주소가 삭제되었습니다."),HttpStatus.OK);
    }
}
