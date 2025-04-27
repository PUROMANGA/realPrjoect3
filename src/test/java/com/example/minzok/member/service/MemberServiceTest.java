package com.example.minzok.member.service;


import com.example.minzok.addresss.entity.Address;
import com.example.minzok.addresss.enums.AddressType;
import com.example.minzok.addresss.repository.AddressRepository;
import com.example.minzok.auth.entity.MyUserDetail;
import com.example.minzok.auth.service.MyUserDetailService;
import com.example.minzok.global.error.CustomNullPointerException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.member.dto.response.MyMemberResponseDto;
import com.example.minzok.member.dto.response.OtherMemberResponseDto;
import com.example.minzok.member.entity.Member;
import com.example.minzok.member.enums.UserRole;
import com.example.minzok.member.repository.MemberRepository;
import com.example.minzok.order.repository.OrderRepository;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.entity.StoreStatus;
import com.example.minzok.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.validateMockitoUsage;

@ExtendWith(MockitoExtension.class)
public class  MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MyUserDetailService myUserDetailService;

    @InjectMocks
    private MemberServiceImpl memberService;



    Member userMember = Member.of(
            "example1@email.com",
            "pw1234",
            UserRole.USER,
            "SAM",
            "nickname",
            LocalDate.of(1995, 4, 24)
    );

    Member managerMember = Member.of(
            "example2@email.com",
            "pw1234",
            UserRole.MANAGER,
            "SAM",
            "nickname",
            LocalDate.of(1995, 4, 24)
    );

    Store store = new Store("가게이름",
            "가게입니다",
            LocalTime.of(10,00),
            LocalTime.of(20, 00),
            17000,
            managerMember);

    Address address = Address.of(
            "지번주소",
            "상세주소",
            AddressType.DEFAULT,
            userMember
    );


    @Test
    @DisplayName("내 프로필 조회 성공")
    public void getMyMemberSuccess(){

        //given

        MyUserDetail myUserDetail = new MyUserDetail(userMember);

        store.setStoreStatus(StoreStatus.OPEN);

        given(memberRepository.findMemberByEmailOrElseThrow(anyString())).willReturn(userMember);

        //when
        MyMemberResponseDto result = memberService.findMe(myUserDetail);

        //then
        assertNotNull(result);
        assertEquals(userMember.getEmail(), result.getEmail());
    }

    @Test
    @DisplayName("상대 프로필 조회 성공")
    public void getOtherMemberSuccess(){

        //given

        MyUserDetail myUserDetail = new MyUserDetail(managerMember);
        ReflectionTestUtils.setField(userMember, "id", 1L);
        ReflectionTestUtils.setField(managerMember, "id", 2L);
        List<Store> storeList = List.of(store);

        given(memberRepository.findMemberByEmailOrElseThrow(anyString())).willReturn(managerMember);
        given(memberRepository.findMemberByIdOrElseThrow(userMember.getId())).willReturn(userMember);
        given(storeRepository.findStoreByMemberEmailAndStoreStatus(managerMember.getEmail(), StoreStatus.CRUSH)).willReturn(storeList);
        given(addressRepository.findAddressByAddressTypeAndMember_Email(AddressType.DEFAULT, userMember.getEmail())).willReturn(address);

        //when
        OtherMemberResponseDto result = memberService.findOther(userMember.getId(), myUserDetail);

        //then
        assertNotNull(result);
    }






}
