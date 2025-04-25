package com.example.minzok.addresss.service;

import com.example.minzok.addresss.dto.AddressRequestDto;
import com.example.minzok.addresss.dto.AddressResponseDto;
import com.example.minzok.addresss.entity.Address;
import com.example.minzok.addresss.enums.AddressType;
import com.example.minzok.addresss.repository.AddressRepository;
import com.example.minzok.global.error.CustomNullPointerException;
import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.auth.entity.MyUserDetail;
import com.example.minzok.member.entity.Member;
import com.example.minzok.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
            throw new CustomRuntimeException(ExceptionCode.ADDRESS_MAX_EXCEEDED);
        }

        Member member = memberRepository.findMemberByEmailOrElseThrow(myUserDetail.getUsername());

        Address address = Address.of(dto.getLotNumberAddress(), dto.getDetailAddress(), AddressType.NORMAL, member);

        addressRepository.save(address);

        return new AddressResponseDto(address.getId(), member.getEmail(), address.getAddressInfo(), address.getAddressType().toString());

    }

    /**
     * 입력된 유저 정보로 주소 리스트 조회
     * @param myUserDetail
     * @return
     */
    @Override
    public List<AddressResponseDto> findAddressByMember(MyUserDetail myUserDetail) {
        return addressRepository.findAddressByMember_Email(myUserDetail.getUsername()).stream().map(AddressResponseDto::toDto).toList();
    }

    /**
     * 주소 타입을 전부 일반 주소로 변경하고 DB에 반영
     * 그 중 선택된 주소륻 대표 주소로 변경
     * @param id
     * @param myUserDetail
     * @return
     */
    @Transactional
    @Override
    public List<AddressResponseDto> updateAddressType(Long id, MyUserDetail myUserDetail) {

        List<Address> addressList = addressRepository.findAddressByMember_Email(myUserDetail.getUsername());

        Address selected = addressList.stream().filter(a -> a.getId().equals(id)).findFirst().orElseThrow(
                () -> new CustomNullPointerException(ExceptionCode.CANT_FIND_ADDRESS));

        for (Address address : addressList) {
            address.updateAddressType(AddressType.NORMAL);
        }

        addressRepository.flush();

        selected.updateAddressType(AddressType.DEFAULT);

        return addressList.stream().map(AddressResponseDto::toDto).toList();
    }

    /**
     * 지정된 주소 삭제
     * 해당 유저의 주소가 1개밖에 없을때, 지우려는 주소가 대표 주소일때 예외가 발생합니다.
     * @param id
     * @param myUserDetail
     */
    @Transactional
    @Override
    public void deleteAddress(Long id, MyUserDetail myUserDetail) {

        int addressCount = addressRepository.countAddressByWithdrawnIsFalseAndMember_Email(myUserDetail.getUsername());

        if(addressCount==1){
            throw new CustomRuntimeException(ExceptionCode.ADDRESS_MIN_EXCEEDED);
        }

        Address address = addressRepository.findAddressByIdOrElseThrow(id);

        if(address.getAddressType().equals(AddressType.DEFAULT)){
            throw new CustomRuntimeException(ExceptionCode.ADDRESS_DEFAULT_NOT_DELETED);
        }

        addressRepository.delete(address);
    }

    /**
     * 주소의 주인이 접속 회원과 일치하는지 확인하는 매서드
     * @param addressId
     * @param email
     * @return
     */
    @Override
    public boolean matchMember(Long addressId, String email) {
        return addressRepository.findById(addressId)
                .map(addr -> addr.getMember().getEmail().equals(email))
                .orElse(false);
    }


}
