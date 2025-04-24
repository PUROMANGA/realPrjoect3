package com.example.minzok.addresss.service;

import com.example.minzok.addresss.dto.AddressRequestDto;
import com.example.minzok.addresss.dto.AddressResponseDto;
import com.example.minzok.addresss.entity.Address;
import com.example.minzok.addresss.enums.AddressType;
import com.example.minzok.addresss.repository.AddressRepository;
import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.global.jwt.MyUserDetail;
import com.example.minzok.member.entity.Member;
import com.example.minzok.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;

    /**
     * 입력한 정보와 로그인된 유저 정보로 주소 저장.
     * 저장된 주소의 갯수가 5개 초과할시 에러가 발생합니다.
     * @param dto
     * @param myUserDetail
     * @return
     */
    @Transactional
    @Override
    public AddressResponseDto createAddress(AddressRequestDto dto, MyUserDetail myUserDetail){

        int addressCount = addressRepository.countAddressByWithdrawnIsFalseAndMember_Email(myUserDetail.getUsername());

        if(addressCount == 5){
            throw new CustomRuntimeException(ExceptionCode.ADDRESS_OVER);
        }

        Member member = memberRepository.findMemberByEmailOrElseThrow(myUserDetail.getUsername());

        Address address = Address.of(dto.getLotNumberAddress(), dto.getDetailAddress(), AddressType.NORMAL, member);

        addressRepository.save(address);

        return new AddressResponseDto(member.getEmail(), address.getAddressInfo());

    }


}
