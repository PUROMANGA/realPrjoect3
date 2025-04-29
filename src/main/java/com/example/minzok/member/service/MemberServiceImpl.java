package com.example.minzok.member.service;

import com.example.minzok.addresss.entity.Address;
import com.example.minzok.addresss.enums.AddressType;
import com.example.minzok.addresss.repository.AddressRepository;
import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.auth.entity.MyUserDetail;
import com.example.minzok.member.dto.request.MemberDeleteRequestDto;
import com.example.minzok.member.dto.request.MemberUpdateRequestDto;
import com.example.minzok.member.dto.response.MemberStoreOrderCountDto;
import com.example.minzok.member.dto.response.MyMemberResponseDto;
import com.example.minzok.member.dto.response.OtherMemberResponseDto;
import com.example.minzok.member.entity.Member;
import com.example.minzok.member.repository.MemberRepository;
import com.example.minzok.order.entity.OrderStatus;
import com.example.minzok.order.repository.OrderRepository;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.entity.StoreStatus;
import com.example.minzok.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public MyMemberResponseDto findMe(MyUserDetail myUserDetail) {

        Member member = memberRepository.findMemberByEmailOrElseThrow(myUserDetail.getUsername());

        return new MyMemberResponseDto(
                member.getId(),
                member.getEmail(),
                member.getUserRole().toString(),
                member.getName(),
                member.getNickname(),
                member.getBirth(),
                myUserDetail.getAddress(),
                member.getCreatTime(),
                member.getModifiedTime()
        );
    }

    @Override
    public OtherMemberResponseDto findOther(Long id, MyUserDetail myUserDetail) {

        Member member = memberRepository.findMemberByEmailOrElseThrow(myUserDetail.getUsername());

        Member other = memberRepository.findMemberByIdOrElseThrow(id);

        if (member.getId().equals(other.getId())) {
            throw new CustomRuntimeException(ExceptionCode.MEMBER_NO_ACCESS);
        }

        List<Store> storeList = storeRepository.findStoreByMemberEmailAndStoreStatus(member.getEmail(), StoreStatus.CRUSH);

        List<MemberStoreOrderCountDto> memberStoreOrderCountDtoList = storeList.stream().map(store -> {
            int orderCount = orderRepository.countByStoreAndMemberAndOrderStatusNot(
                    store, other, OrderStatus.WAITING
            );
            return new MemberStoreOrderCountDto(store.getStoreName(), orderCount);
        }).toList();

        Address address = addressRepository.findAddressByAddressTypeAndMember_Email(AddressType.DEFAULT, other.getEmail());

        return new OtherMemberResponseDto(
                other.getId(),
                other.getName(),
                other.getNickname(),
                address.getAddressInfo(),
                other.getCreatTime(),
                memberStoreOrderCountDtoList);
    }

    @Transactional
    @Override
    public MyMemberResponseDto updateMember(
            Long id,
            MyUserDetail myUserDetail,
            MemberUpdateRequestDto dto
    ) {

        if(!Objects.equals(dto.getNewPassword(), dto.getNewPasswordCheck())){
            throw new CustomRuntimeException(ExceptionCode.PASSWORD_MISMATCH);
        }

        Member member = memberRepository.findMemberByEmailOrElseThrow(myUserDetail.getUsername());
        member.validatePassword(dto.getOldPassword(), passwordEncoder);

        member.updateMember(passwordEncoder.encode(dto.getNewPassword()), dto.getNickname(), dto.getBirth());

        Address address = addressRepository.findAddressByAddressTypeAndMember_Email(AddressType.DEFAULT, myUserDetail.getUsername());

        return new MyMemberResponseDto(
                member.getId(),
                member.getEmail(),
                member.getUserRole().toString(),
                member.getName(),
                member.getNickname(),
                member.getBirth(),
                address.getAddressInfo(),
                member.getCreatTime(),
                member.getModifiedTime()
        );
    }

    @Override
    public void deleteMember(MyUserDetail myUserDetail, MemberDeleteRequestDto dto) {
        Member member = memberRepository.findMemberByEmailOrElseThrow(myUserDetail.getUsername());
        member.validatePassword(dto.getPassword(), passwordEncoder);
        memberRepository.delete(member);
    }

    /**
     * 해당 아이디의 유저가 접속 회원과 일치하는지 확인하는 매서드
     * @param userId
     * @param email
     * @return
     */
    @Override
    public boolean matchMember(Long userId, String email) {
        return memberRepository.findById(userId)
                .map(member -> member.getEmail().equals(email))
                .orElse(false);
    }


}
