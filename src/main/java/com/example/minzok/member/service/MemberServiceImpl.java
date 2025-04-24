package com.example.minzok.member.service;

import com.example.minzok.addresss.entity.Address;
import com.example.minzok.addresss.enums.AddressType;
import com.example.minzok.addresss.repository.AddressRepository;
import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.global.jwt.MyUserDetail;
import com.example.minzok.member.dto.response.MemberStoreOrderCountDto;
import com.example.minzok.member.dto.response.MyMemberResponseDto;
import com.example.minzok.member.dto.response.OtherMemberResponseDto;
import com.example.minzok.member.entity.Member;
import com.example.minzok.member.repository.MemberRepository;
import com.example.minzok.order.entity.Order;
import com.example.minzok.order.entity.OrderStatus;
import com.example.minzok.order.repository.OrderRepository;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.entity.StoreStatus;
import com.example.minzok.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;


    @Override
    public MyMemberResponseDto findMe(MyUserDetail myUserDetail) {

        Member member = memberRepository.findMemberByEmailOrElseThrow(myUserDetail.getUsername());

        return new MyMemberResponseDto(
                member.getId(),
                member.getUserRole().toString(),
                member.getEmail(),
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
}
